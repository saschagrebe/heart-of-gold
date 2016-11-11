#include <SPI.h>
#include <UIPEthernet.h>

#include <Adafruit_DotStar.h>

#define LOGO_NUMPIXELS         4
#define STATUS_RING_NUMPIXELS  15

// Here's how to control the LEDs from any two pins:
#define LOGO_DATAPIN         5
#define LOWER_RING_DATAPIN   6
#define UPPER_RING_DATAPIN   7
#define CLOCKPIN             8
Adafruit_DotStar logo = Adafruit_DotStar(LOGO_NUMPIXELS, LOGO_DATAPIN, CLOCKPIN, DOTSTAR_RGB);
Adafruit_DotStar lowerRing = Adafruit_DotStar(STATUS_RING_NUMPIXELS, LOWER_RING_DATAPIN, CLOCKPIN, DOTSTAR_RGB);
Adafruit_DotStar upperRing = Adafruit_DotStar(STATUS_RING_NUMPIXELS, UPPER_RING_DATAPIN, CLOCKPIN, DOTSTAR_RGB);
uint32_t lowerRingColors[15] = {};
uint32_t upperRingColors[15] = {};

// networking stuff
EthernetServer server = EthernetServer(5999);
uint8_t mac[6] = {0x02, 0x3C, 0xBB, 0xF0, 0x0E, 0xB7};

void setup() {
  Serial.begin(9600);
  setupLeds();
  setupNetwork();
}

void setupLeds() {
  // Initialize pins for output
  logo.begin();
  lowerRing.begin();
  upperRing.begin();

  // init logo
  logo.setPixelColor(0, 0, 100, 255);
  logo.setPixelColor(1, 125, 255, 0);
  logo.setPixelColor(2, 0, 255, 0);
  logo.setPixelColor(3, 255, 0, 255);
  logo.setBrightness(255);
  logo.show();
  
  // Turn all LEDs off ASAP
  lowerRing.show();
  upperRing.show();
}

void setupNetwork() {
  // use DHCP for gathering an IP address
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed DHCP");
  }
  Serial.println(Ethernet.localIP());
  
  server.begin();
}

String cmd = "";
EthernetClient client;
boolean rotateLower = false;
boolean rotateUpper = false;

void loop() {
  client = server.available();
  if (client) {
    while (client.available() > 0) {
      readTelnetCommand(client.read());
    }
    
    updateLeds();
  }
  
  delay(20);
}

void updateLeds() {
  for (int i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    lowerRing.setPixelColor(i, lowerRingColors[i]);
  }
  lowerRing.show();
  
  for (int i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    upperRing.setPixelColor(i, upperRingColors[i]);
  }
  upperRing.show();
}

void readTelnetCommand(char c) {
  if (c != '\n' && c != '\r') {
    cmd += c;
  }
  
  if (c == '\n') {
    parseCommand();
    cmd = "";
  }
}

// - Help -
// r<u|l><0|1> : rotate <upper|lower> <0|1>
// s<u|l><pixel><color>
// q
void parseCommand() {
  Serial.println(cmd);
  if(cmd.charAt(0) == 'q') {
      Serial.println("Bye");
      client.flush();
      client.stop();
      
  } else if(cmd.charAt(0) == 'r' && cmd.length() == 3) {
      rotateCommand();
      
  } else if(cmd.charAt(0) == 's' && cmd.length() > 4) {
      switchCommand();
      
  }
}

void rotateCommand() {
  // ru1
  boolean rotate = cmd.charAt(2) == '1';
  if (cmd.charAt(1) == 'u') {
    rotateUpper = rotate;
  } else {
    rotateLower = rotate;
  }
}

void switchCommand() {
  // su01255
  int pixel = cmd.substring(2, 4).toInt();
  int color = cmd.substring(4).toInt();
  if (cmd.charAt(1) == 'u') {
    upperRingColors[pixel] = color;
  } else {
    lowerRingColors[pixel] = color;
  }
}

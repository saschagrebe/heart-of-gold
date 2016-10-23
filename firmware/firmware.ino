#include <UIPEthernet.h>

#include <Adafruit_DotStar.h>

#define LOGO_NUMPIXELS         4
#define STATUS_RING_NUMPIXELS  30

// Here's how to control the LEDs from any two pins:
#define LOGO_DATAPIN         5
#define LOWER_RING_DATAPIN   6
#define UPPER_RING_DATAPIN   7
#define CLOCKPIN             8
Adafruit_DotStar logo = Adafruit_DotStar(LOGO_NUMPIXELS, LOGO_DATAPIN, CLOCKPIN, DOTSTAR_BRG);
Adafruit_DotStar lowerRing = Adafruit_DotStar(STATUS_RING_NUMPIXELS, LOWER_RING_DATAPIN, CLOCKPIN, DOTSTAR_BRG);
Adafruit_DotStar upperRing = Adafruit_DotStar(STATUS_RING_NUMPIXELS, UPPER_RING_DATAPIN, CLOCKPIN, DOTSTAR_BRG);
uint32_t lowerRingColors[30] = {};
uint32_t upperRingColors[30] = {};

// networking stuff
EthernetServer server = EthernetServer(5999);
uint8_t mac[6] = {0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02};

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
  
  // Turn all LEDs off ASAP
  logo.show();
  lowerRing.show();
  upperRing.show();
}

void setupNetwork() {
  // use DHCP for gathering an IP address
  Ethernet.begin(mac);
  Serial.println(Ethernet.localIP());
  
  server.begin();
}

boolean connected = false;
String cmd = "";
EthernetClient client;
boolean rotateLower = false;
boolean rotateUpper = false;

void loop() {
  client = server.available();
  if (client) {
    if (!connected) {
      client.flush();
      connected = true;
    }

    while (client.available() > 0) {
      readTelnetCommand(client.read());
    }
    // TODO update LED states
  }
  
  delay(10);
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
// switch <upper|lower> <pixel> <color>
// quit
void parseCommand() {
  Serial.println(cmd);
  if(cmd.charAt(0) == 'q') {
      client.stop();
      connected = false;
      
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

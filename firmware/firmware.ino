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
  setupLeds();
  
  // use DHCP for gathering an IP address
  if (Ethernet.begin(mac) == 0) {
    dhcpFailure();
  } else {
    dhcpSuccess();
  }
  
  server.begin();
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

void dhcpSuccess() {
  // init logo
  logo.setPixelColor(0, 0, 100, 255);
  logo.setPixelColor(1, 125, 255, 0);
  logo.setPixelColor(2, 0, 255, 0);
  logo.setPixelColor(3, 255, 0, 255);
  logo.setBrightness(255);
  logo.show();
}

void dhcpFailure() {
  // init logo
  logo.setPixelColor(0, 0, 0, 255);
  logo.setPixelColor(1, 0, 0, 255);
  logo.setPixelColor(2, 0, 0, 255);
  logo.setPixelColor(3, 0, 0, 255);
  logo.setBrightness(255);
  logo.show();
}

String cmd = "";
EthernetClient client;
byte lowerOffset = 0;
byte upperOffset = 0;
int lowerLoopCount = 0;
int upperLoopCount = 0;

// 1000ms / 20ms = 50
int lowerWhirlSpeed = 50;
int upperWhirlSpeed = 50;

void loop() {
  client = server.available();
  if (client) {
    while (client.available() > 0) {
      readTelnetCommand(client.read());
    }
  }

  updateLeds();
  delay(20);
}

void updateLeds() {
  // update lower LEDs
  for (byte i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    lowerRing.setPixelColor((i + lowerOffset) % STATUS_RING_NUMPIXELS, lowerRingColors[i]);
  }
  lowerRing.show();

  // lower whirl
  if (++lowerLoopCount >= lowerWhirlSpeed) {
    if (++lowerOffset >= STATUS_RING_NUMPIXELS) {
      lowerOffset = 0;
    }
    lowerLoopCount = 0;
  }

  // update uper LEDs
  for (byte i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    upperRing.setPixelColor((i + upperOffset) % STATUS_RING_NUMPIXELS, upperRingColors[i]);
  }
  upperRing.show();

  // upper whirl
  if (++upperLoopCount >= upperWhirlSpeed) {
    if (++upperOffset >= STATUS_RING_NUMPIXELS) {
      upperOffset = 0;
    }
    upperLoopCount = 0;
  }
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
// w<u|l><whirlSpeed>
// s<u|l><pixel><color>
// q
void parseCommand() {
  if(cmd.charAt(0) == 'q') {
      client.flush();
      client.stop();
      
  } else if(cmd.charAt(0) == 'w' && cmd.length() > 2) {
      setWhirlSpeed();
     
  } else if(cmd.charAt(0) == 's' && cmd.length() > 4) {
      switchCommand();
      
  }
}

void setWhirlSpeed() {
  if (cmd.charAt(1) == 'u') {
    upperWhirlSpeed = cmd.substring(2).toInt();
  } else {
    lowerWhirlSpeed = cmd.substring(2).toInt();
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

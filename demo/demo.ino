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
uint32_t lowerRingColors[15] = { 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
uint32_t upperRingColors[15] = { 0x7F00, 0x7F00, 0xFF, 0xFF, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00, 0x7F00 };

void setup() {
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

int loops = 0;
byte offset = 0;

void loop() {
  loops++;
  
  updateLeds();
  delay(500);
}

void updateLeds() {
  for (byte i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    lowerRing.setPixelColor((i + offset) % STATUS_RING_NUMPIXELS, lowerRingColors[i]);
  }
  lowerRing.show();

  for (byte i = 0; i < STATUS_RING_NUMPIXELS; i++) {
    upperRing.setPixelColor((i + offset) % STATUS_RING_NUMPIXELS, upperRingColors[i]);
  }
  upperRing.show();

  if (++offset >= STATUS_RING_NUMPIXELS) {
    offset = 0;
  }
}

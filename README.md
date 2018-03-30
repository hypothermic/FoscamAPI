# FoscamLib
Easily connect to Foscam IP camera's with this lightweight Java library. One command is all it takes.  
Tested on the FI-9900P, although the commands should work on almost all models.

## Example
```java
// Connect to the camera
Foscam camera = new Foscam("192.168.1.40", 88, "admin", "password");

// Do whatever you want
camera.setSharpness(90);
...
```

## Features
Here is a full list of commands:

```java
// Image settings
- setBrightness(0-100);
- setContrast(0-100);
- setSaturation(0-100);
- setSharpness(0-100);
- resetImageSetting();

// Image manipulation
- isMirrored();
- isFlipped();
- setMirrored(true/false);
- setFlipped(true/false);

// Networking
- isDHCP();
- getIP();
- getNetworkGateway();
- getNetworkMask();
- getNetworkDNSPrimary();
- getNetworkDNSSecondary();
- getURL();
- getInterfaceURL();

- getPortInfo();
    - getWebPort();
    - getMediaPort();
    - getHttpsPort();
    - getOnvifPort();
    - getRtspPort();
- setPortInfo(PortInfo);

- isUPnPEnabled();
- setUPNP(true/false);

- isP2PEnabled();
- setP2P();
- getP2PPort();
- setP2PPort();

- getFTPConfig();
- setFTPConfig(FTPConfig);

- isFirewallEnabled();
- getFirewallRule();
- getFirewallEntry(0-7);

// Camera controls
- snapPicture();
- openInfraLed();
- closeInfraLed();
- getInfraLedMode();
- setInfraLedMode(0-1);
- rebootSystem();
- exportConfig();
- exportedConfigURL();
- getLogEntries(count, offset);

// Camera info
- getName();
- setName("MyCamera");
- getProductAppVer();
- getProductModel();
- getProductModelName();
- getProductLanguage();
- getProductSensorType();
- getProductWifiType();

- isAudioSupported();
- isIoAlarmSupported();
- isOnvifSupported();
- isP2PSupported();
- isSdcardSupport();
- isProductOutdoorModel();
- isProductZoomModel();
- isPtModel();
- isRs485Supported();
- isRtspSupported();
- isTalkSupported();
- isWPSSupported();

// Deprecated
- doesCameraSupportOnvif();
- doesCameraSupportRtsp();
```

## Changelog
v1.03
- Added all info flags
- Added DeviceInfo
- doesCameraSupportOnvif and Rtsp are now deprecated.
- Added firewall controls
- Added log controls
- Added config exporting control
- Added snapPicture()

v1.02
- Added FTP configuration controls
- Added Infrared light and config controls
- Added getName and setName

v1.01
- Added PortInfo and port getters
- Added get/set for UPnP

v1.00
- Initial release

## Mentions
FoscamLib is not affiliated, associated, authorized, endorsed by, or in any way officially connected with Shenzhen Foscam Intelligent Technology Limited, or any of its subsidiaries or its affiliates. The official Foscam website can be found at https://www.foscam.com. The name “Foscam” as well as related names, marks, emblems and images are registered trademarks of Shenzhen Foscam Intelligent Technology Limited.

## Sources
`http://www.camarasip.es/descarga/IP_Camera_CGI_(SDK).pdf`
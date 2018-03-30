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
Here is a full list of commands (sorted by relevancy):

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
- doesCameraSupportOnvif();
- doesCameraSupportRtsp();
- isDHCP();
- isUPnPEnabled();
- setUPNP(true/false);
```

## Changelog
v1.01
- Added PortInfo and port getters
- Added get/set for UPnP

v1.00
- Initial release

## Mentions
FoscamLib is not affiliated, associated, authorized, endorsed by, or in any way officially connected with Shenzhen Foscam Intelligent Technology Limited, or any of its subsidiaries or its affiliates. The official Foscam website can be found at https://www.foscam.com. The name “Foscam” as well as related names, marks, emblems and images are registered trademarks of Shenzhen Foscam Intelligent Technology Limited.

## Sources
`http://www.camarasip.es/descarga/IP_Camera_CGI_(SDK).pdf`
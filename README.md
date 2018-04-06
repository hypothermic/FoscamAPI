# FoscamLib
Easily connect to Foscam IP camera's with this lightweight Java library. One command is all it takes.  
Tested on the FI-9900P, although the commands should work on almost all models.

## Example
```java
// Connect to the camera
Foscam camera = new Foscam("192.168.1.40", 88, "username", "password");

// Do whatever you want
camera.setSharpness(90);
...
```

## Maven dependency
```xml
<repository>
  <id>foscamlib-mvn-repo</id>
  <url>https://raw.github.com/hypothermic/foscamlib/mvn-repo/</url>
</repository>

<dependency>
  <groupId>nl.hypothermic</groupId>
  <artifactId>foscamlib</artifactId>
  <!-- If you're using Maven3, specify version num -->
  <version>LATEST</version>
</dependency>
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
- setP2P(true/false);
- getP2PPort();
- setP2PPort(value);

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

// Camera streams
- getMainVideoStreamType();
- getSubVideoStreamType();
- getH264FrameRefMode()
- setMainVideoStreamType(0-3);
- setSubStreamFormat(0-1);
- setH264FrameRefMode(int mode)
- getMJStreamURL();

- setSnapConfig();
- getSnapConfig();

// Camera account control
- addAccount(...);
- deleteAccount(...);
- changePassword(...);
- changeUsername(...);
- getUserList();
- getUserListWithIndexes();
- getSessionList();
- getSessionListWithIndexes();

// OSD settings
- getOSDSettings();
- setOSDSettings(...);
- isOSDMaskEnabled();
- setOSDMaskState(true/false);

// Alarm settings
- getMotionDetectConfig();
- setMotionDetectConfig(...);
- getLocalAlarmRecordConfig();
- setLocalAlarmRecordConfig(...);

// PTZ controls
- ptzMoveUp();
- ptzMoveDown();
- ptzMoveLeft();
- ptzMoveRight();
- ptzMoveTopLeft();
- ptzMoveTopRight();
- ptzMoveBottomLeft();
- ptzMoveBottomRight();
- ptzStopMoving();
- ptzResetPosition();
- getPTZSpeed();
- setPTZSpeed(0-4);

// Deprecated, kept for backwards compatibility
- doesCameraSupportOnvif();
- doesCameraSupportRtsp();
```

## Changelog
v1.05
- Added user account controls
- Added local alarm record config
- Added PTZ controls and PTZ speed controls
- Added motion detection functionality (beta):
    - Motion detect config
    - Motion detect schedule map
    - Motion detect area map
- Added H264 stream settings
- Fixed NetExecutor (see commit changes)

v1.04
- Added camera stream controls.
- Created Maven Repository. See "Maven dependency" above for details. (branch "mvn-repo")

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

## License
The license can be found [here](./LICENSE.txt) or in the root folder of the project.

## Sources
`http://www.camarasip.es/descarga/IP_Camera_CGI_(SDK).pdf`
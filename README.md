# QRcode
| EndPoint | Description | Mapping |
| --- | --- | --- |
| localhost:8080/qr?text=?&width=?&height=? | Generate and return a QR Code | GET
| localhost:8080/qr?text=?&width=?&height=?&filePath=? | Generate and save a QR Code | POST
| localhost:8080/qr/read?qrCodeImage=? | Encode a QR Code From File as Param | GET
| localhost:8080/qr/readMP | Encode a QR Code From MultipartFile as Part | GET

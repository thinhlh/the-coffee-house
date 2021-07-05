# The Coffee House
A coffee client application can be executed natively in Android based mobiles

* For more information, view this [link](https://www.notion.so/Mobile-Programming-2563b2133f554ccd9f24fd681c1b2bb9)
* Working rules:
  * For the features growth of the application in the future, please create a new branch for each feature and pull request as well as request merge only.
  * Must **NOT** commit directly to **main** branch in any case. 
  * Pull or fetch before working in local

## Giảng viên hướng dẫn
 * Thầy Nguyễn Tấn Toàn

## Sinh viên tham gia đồ án
 * Lê Hoàng Thịnh - 19520285
 * Huỳnh Minh Nhật - 19521952
 * Bùi Thiện Nhân - 19521927
 
## Project Description and Content
 * Ứng dụng cung cấp thông tin cho khách hàng về thương hiệu The Coffee House tại Việt Nam. 
 * Cho phép người dùng mua, sử dụng các sản phẩm mà The Coffee House cung cấp cũng như kiểm tra thông tin thành viên cá nhân, tham gia nhận những ưu đãi từ nhà cung cấp.
 * Về Tính Năng ở phiên bản này:
     * Cá nhân hóa thực đơn của khách hàng trong phần lưu trữ các sản phẩm yêu thích.
     * Kiểm tra, tìm kiếm, sử dụng các ưu đãi được cung cấp tiện dụng hơn trong phần Order.
     * Tìm kiếm, tra cứu các cửa hàng có mặt tại khu vực.
     * Cung cấp trang tra cứu, kiểm tra tích điểm, mức bậc thành viên, ưu đãi.
     * Xem lại những chi tiết những đơn hàng đã được đặt và thanh toán của tài khoản.
     * Chỉnh sửa các thông tin cá nhân dễ dàng.
     * The Coffee House luôn lắng nghe các đóng góp, phản hồi của khách hàng thông qua mục "feedbacks" được ứng dụng cung cấp.
 * Cho phép quản trị viên có thể thêm, xóa, chỉnh sửa các dữ liệu cung cấp cho khách hàng ngay trên ứng dụng với tài khoản được cấp quyền.
## Install Requirement
 * Ứng dụng dược tương thích với hầu hết các nền tảng android từ 5.0.
 * Khách hàng có thể đăng ký tài khoản ngay trên ứng dụng hoặc đăng nhập thông qua tài khoản Google hoặc Facebook.

## Development Enviroment
 * Ứng dụng được xây dựng và phát triển trên Android Studio với Android SDK 30
 * Phiên bản build gradle 4.3.2
 * Các vấn đề trong quá trình thực thi ứng dụng trên một số nền tảng
 * [Máy mac M1 chrome bị lỗi](https://github.com/google/android-emulator-m1-preview/issues/1#issuecomment-812989474)

## Dependencies
 * Cơ sở dữ liệu của ứng dụng được quản lý trên [Firestore](https://firebase.google.com/docs/firestore).
 * Có sử dụng đăng qua Google, Facebook qua Firebase Authenticate do [Google](https://firebase.google.com/docs/auth/android/google-signin),[Facebook](https://firebase.google.com/docs/auth/android/facebook-login) cung cấp.
 * Heroku để làm server cho REST API

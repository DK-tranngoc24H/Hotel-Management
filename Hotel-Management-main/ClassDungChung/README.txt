THƯ MỤC CLASSDUNGCHUNG - HOTEL MANAGEMENT SYSTEM
==================================================

Các thành phần dùng chung:
1. INhapXuat.java:
   - Interface chuẩn định nghĩa 2 phương thức: void nhap(Scanner sc); void xuat();
   - Được kế thừa / triển khai bởi các lớp đối tượng trong các module.

2. ThongBao.java:
   - Lớp tiện ích cung cấp các mẫu thông báo chuẩn: inTieuDe, inThanhCong, inCanhBao, inLoi.
   - Định nghĩa các hằng số thông báo lỗi dùng chung (LOI_DE_TRONG, LOI_SO_AM, LOI_SO_KHONG_HOP_LE,...).

3. KiemTraLoi.java:
   - Tiện ích kiểm tra và xác thực dữ liệu nhập từ Scanner:
     + nhapChuoiKhongRong
     + nhapSoNguyenDuong
     + nhapSoThucDuong
     + nhapSoNguyenTrongKhoang
     + nhapCoKhong
     + nhapTrangThaiPhong
     + nhapLoaiView

4. Main.java:
   - Điểm kiểm thử dùng chung cho toàn bộ các thành phần trong ClassDungChung.
   - Hỗ trợ 2 chế độ:
     + Tự động (Automated Test Suite): Kiểm tra tất cả hàm và kịch bản lỗi, xuất PASS/FAIL.
     + Tương tác (Interactive Test): Menu console trực quan để người dùng kiểm thử từng tính năng.

Cách biên dịch và chạy kiểm thử:
--------------------------------
Cách 1: Từ thư mục gốc (Hotel-Management):
   javac -encoding UTF-8 ClassDungChung/*.java
   java -cp ClassDungChung Main auto       (chạy kiểm thử tự động)
   java -cp ClassDungChung Main            (chạy menu tương tác)

Cách 2: Từ bên trong thư mục ClassDungChung:
   cd ClassDungChung
   javac -encoding UTF-8 *.java
   java Main auto                          (chạy kiểm thử tự động)
   java Main                               (chạy menu tương tác)


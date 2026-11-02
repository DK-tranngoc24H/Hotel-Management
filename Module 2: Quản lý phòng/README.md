# 🏨 DỰ ÁN QUẢN LÝ PHÒNG KHÁCH SẠN (PHIÊN BẢN HOÀN CHỈNH)

Dự án này là phiên bản nâng cấp hoàn chỉnh được xây dựng dựa trên 4 class của bạn, bổ sung các tiêu chuẩn OOP chuẩn mực và giải quyết toàn bộ các điểm "thiếu thiếu" trước đó.

---

## 🏗️ 1. SƠ ĐỒ LỚP (CLASS ARCHITECTURE)

```
               <<interface>>
                 INhapXuat
               + nhap(Scanner)
               + xuat()
                   ▲
                   │ (implements)
     ┌─────────────┴─────────────┐
     │                           │
+----+-------------------+  +----+-------------------------+
|     TienNghiPhong      |  |          Phong               | <--- (Lớp cha trừu tượng)
+------------------------+  +------------------------------+
| - maTienNghi: String   |  | - maPhong: String            |
| - tenTienNghi: String  |  | - giaCoBan: double           |
| - soLuong: int         |  | - trangThai: String          |
| - tinhTrang: String    |  | - tienNghi: TienNghiPhong    | <--- [Quan hệ Has-A]
+------------------------+  +------------------------------+
                            | + conTrong(): boolean        |
                            | + abstract tinhGiaPhong()    |
                            +------------------------------+
                                           ▲
                                           │ (extends)
                    ┌──────────────────────┴──────────────────────┐
                    │                                             │
+-------------------+-----------+             +-------------------+---------------+
|         PhongStandard         |             |             PhongVIP              |
+-------------------------------+             +-----------------------------------+
| - coGiuongPhu: boolean        |             | - loaiView: String                |
| - PHU_THU_GIUONG_PHU: 100k    |             | - phiDichVuDacBiet: double        |
+-------------------------------+             +-----------------------------------+
| + tinhGiaPhong(): double      |             | + tinhGiaPhong(): double          |
+-------------------------------+             +-----------------------------------+
```

---

## 🌟 2. CÁC NÂNG CẤP ĐÁNG GIÁ ĐÃ ĐƯỢC BỔ SUNG

1. **Quan hệ Chứa (Has-A - Composition)**:
   - `Phong` bây giờ có thuộc tính `private TienNghiPhong tienNghi`. Khi gọi `p.xuat()`, tiện nghi đi kèm phòng đó cũng sẽ được in ra rõ ràng.
2. **Interface `INhapXuat`**:
   - Chuẩn hóa hai phương thức `nhap(Scanner sc)` và `xuat()` cho cả `Phong` và `TienNghiPhong`.
3. **Nghiệp vụ tính giá chuyên sâu**:
   - `PhongVIP`: Tự động tính thêm phụ thu theo View (View Biển +100.000 VNĐ, View Thành phố +50.000 VNĐ).
   - `PhongStandard`: Tính phụ thu giường phụ với hằng số `PHU_THU_GIUONG_PHU = 100.000 VNĐ`.
4. **Xử lý chuỗi thông minh**:
   - `conTrong()` và `isConTot()` hỗ trợ cả nhập có dấu ("trống", "tốt") và không dấu ("trong", "tot").
5. **Đa hình (Polymorphism) hoàn hảo trong `Main.java`**:
   - Duyệt `ArrayList<Phong>`, tự động gọi đúng hàm của `PhongStandard` hoặc `PhongVIP`.
   - Có sẵn chức năng lọc phòng trống và tính tổng doanh thu toàn bộ khách sạn.

---

## 🚀 3. HƯỚNG DẪN BIÊN DỊCH VÀ CHẠY THỬ

Mở terminal tại thư mục này và gõ:
```bash
javac *.java
java Main
```

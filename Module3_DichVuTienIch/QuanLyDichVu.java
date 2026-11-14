<<<<<<< HEAD
package BT_quanLyKhachSan;

public class QuanLyDichVu {
    private final DanhSachDichVu dsDV;
    private final NhapLieu nl;

    public QuanLyDichVu(DanhSachDichVu dsDV, NhapLieu nl) {
        this.dsDV = dsDV;
        this.nl = nl;
    }

    // Tìm dịch vụ theo mã (không phân biệt hoa thường), không có thì trả về null
    public DichVu timTheoMa(String ma) {
        for (DichVu dv : dsDV.getDs()) {
            if (dv.getMaDV().equalsIgnoreCase(ma)) {
                return dv;
            }
        }
        return null;
    }

    public void them() {
        System.out.println("Loại: 1. Ăn uống   2. Spa & Giải trí   3. Thuê xe");
        int loai = nl.nhapSoNguyen("Chọn loại: ", 1, 3);

        DichVu dv;
        if (loai == 1) {
            dv = new DichVuAnUong();
        } else if (loai == 2) {
            dv = new DichVuSpaGiaiTri();
        } else {
            dv = new DichVuThueXe();
        }
        dv.nhap(nl);

        if (timTheoMa(dv.getMaDV()) != null) {
            System.out.println("Mã " + dv.getMaDV() + " đã tồn tại, không thêm được!");
            return;
        }

        dsDV.getDs().add(dv);
        dsDV.ghiFile();
        System.out.println("Đã thêm và lưu file.");
    }

    public void sua() {
        String ma = nl.nhapChuoi("Nhập mã cần sửa: ");
        DichVu dv = timTheoMa(ma);
        if (dv == null) {
            System.out.println("Không tìm thấy mã " + ma);
            return;
        }

        System.out.println("Nhập lại thông tin mới cho dịch vụ " + ma + ":");
        dv.nhap(nl);
        dsDV.ghiFile();
        System.out.println("Đã sửa và lưu file.");
    }

    public void xoa() {
        String ma = nl.nhapChuoi("Nhập mã cần xóa: ");
        DichVu dv = timTheoMa(ma);
        if (dv == null) {
            System.out.println("Không tìm thấy mã " + ma);
            return;
        }

        dsDV.getDs().remove(dv);
        dsDV.ghiFile();
        System.out.println("Đã xóa và lưu file.");
    }
}
=======
package BT_quanLyKhachSan;

public class QuanLyDichVu {
    private final DanhSachDichVu dsDV;
    private final NhapLieu nl;

    public QuanLyDichVu(DanhSachDichVu dsDV, NhapLieu nl) {
        this.dsDV = dsDV;
        this.nl = nl;
    }

    // Tìm dịch vụ theo mã (không phân biệt hoa thường), không có thì trả về null
    public DichVu timTheoMa(String ma) {
        for (DichVu dv : dsDV.getDs()) {
            if (dv.getMaDV().equalsIgnoreCase(ma)) {
                return dv;
            }
        }
        return null;
    }

    public void them() {
        System.out.println("Loại: 1. Ăn uống   2. Spa & Giải trí   3. Thuê xe");
        int loai = nl.nhapSoNguyen("Chọn loại: ", 1, 3);

        DichVu dv;
        if (loai == 1) {
            dv = new DichVuAnUong();
        } else if (loai == 2) {
            dv = new DichVuSpaGiaiTri();
        } else {
            dv = new DichVuThueXe();
        }
        dv.nhap(nl);

        if (timTheoMa(dv.getMaDV()) != null) {
            System.out.println("Mã " + dv.getMaDV() + " đã tồn tại, không thêm được!");
            return;
        }

        dsDV.getDs().add(dv);
        dsDV.ghiFile();
        System.out.println("Đã thêm và lưu file.");
    }

    public void sua() {
        String ma = nl.nhapChuoi("Nhập mã cần sửa: ");
        DichVu dv = timTheoMa(ma);
        if (dv == null) {
            System.out.println("Không tìm thấy mã " + ma);
            return;
        }

        System.out.println("Nhập lại thông tin mới cho dịch vụ " + ma + ":");
        dv.nhap(nl);
        dsDV.ghiFile();
        System.out.println("Đã sửa và lưu file.");
    }

    public void xoa() {
        String ma = nl.nhapChuoi("Nhập mã cần xóa: ");
        DichVu dv = timTheoMa(ma);
        if (dv == null) {
            System.out.println("Không tìm thấy mã " + ma);
            return;
        }

        dsDV.getDs().remove(dv);
        dsDV.ghiFile();
        System.out.println("Đã xóa và lưu file.");
    }
}
>>>>>>> 2c424b4259f2ca0ec5a2e8a0ce5700237fce3469

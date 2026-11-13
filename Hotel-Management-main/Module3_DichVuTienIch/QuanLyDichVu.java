

import java.util.ArrayList;

public class QuanLyDichVu {
    private final ArrayList<DichVu> danhSach = new ArrayList<>();
    private final NhapLieu nhapLieu;

    public QuanLyDichVu(NhapLieu nhapLieu) {
        this.nhapLieu = nhapLieu;
    }

    // 1. Thêm dịch vụ vào danh sách
    public void them(DichVu dv) {
        danhSach.add(dv);
        System.out.println("Đã thêm dịch vụ thành công!");
    }

    // 2. Hiển thị toàn bộ danh sách dịch vụ
    public void hienThi() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách dịch vụ đang trống!");
            return;
        }
        System.out.println("\n--- DANH SÁCH DỊCH VỤ ---");
        for (DichVu dv : danhSach) {
            dv.xuat();
            System.out.println("----------------------------------------");
        }
    }

    // 3. Tìm kiếm dịch vụ theo mã (trả về null nếu không thấy)
    public DichVu timKiem(String maDV) {
        for (DichVu dv : danhSach) {
            if (dv.getMaDV().equalsIgnoreCase(maDV)) {
                return dv;
            }
        }
        return null;
    }

    // 4. Xóa dịch vụ theo mã
    public void xoa(String maDV) {
        DichVu dv = timKiem(maDV);
        if (dv != null) {
            danhSach.remove(dv);
            System.out.println("Đã xóa thành công dịch vụ mã: " + maDV);
        } else {
            System.out.println("Không tìm thấy mã dịch vụ cần xóa: " + maDV);
        }
    }

    // 5. Sửa thông tin dịch vụ theo mã
    public void sua(String maDV) {
        DichVu dv = timKiem(maDV);
        if (dv != null) {
            System.out.println("Tìm thấy dịch vụ! Nhập lại thông tin mới:");
            dv.nhap(nhapLieu);
            System.out.println("Cập nhật thông tin thành công!");
        } else {
            System.out.println("Không tìm thấy mã dịch vụ cần sửa: " + maDV);
        }
    }
}

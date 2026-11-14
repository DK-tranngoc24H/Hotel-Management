import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KhachHang extends Nguoi {
    Scanner sc = new Scanner(System.in);
    protected String loaikhach;
    // Lich su dat phong cua khach (lien ket voi PhieuDatPhong - Module 4)
    protected List<PhieuDatPhong> lichsudatphong = new ArrayList<>();

    public KhachHang() {
        super();
        this.loaikhach = "";
    }

    public KhachHang(String hoten, String CCCD, String sdt, String diachi, String loaikhach, List<PhieuDatPhong> lichsudatphong) {
        super(hoten, CCCD, sdt, diachi);
        this.loaikhach = "";
        setLoaikhach(loaikhach);
        setLichsudatphong(lichsudatphong);
    }

    @Override
    public void nhap() {
        super.nhap();

        while (true) {
            loaikhach = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap loai khach: ");
            if (!loaikhach.contains(";")) {
                break;
            }
            ThongBao.inLoi("Loai khach khong duoc chua ky tu ';'!");
        }

        // Lich su dat phong khong nhap tay: duoc cap nhat tu dong khi tao
        // PhieuDatPhong (Module 4) thong qua themLichSuDatPhong(...)
        lichsudatphong = new ArrayList<>();
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Loai khach: " + loaikhach);
        System.out.println("Lich su dat phong: ");
        if (lichsudatphong == null || lichsudatphong.isEmpty()) {
            System.out.println("  (Chua co lich su dat phong)");
        } else {
            for (PhieuDatPhong dp : lichsudatphong) {
                System.out.println("  Ma phieu: " + dp.getMaPhieu()
                        + " | Phong: " + dp.getMaPhong()
                        + " | Nhan: " + dp.getNgayNhanStr()
                        + " | Tra: " + dp.getNgayTraStr());
            }
        }
    }

    public String getLoaikhach() {
        return loaikhach;
    }

    public List<PhieuDatPhong> getLichsudatphong() {
        return lichsudatphong;
    }

    // Them 1 phieu vao lich su (bo qua null va phieu trung ma)
    public void themLichSuDatPhong(PhieuDatPhong phieu) {
        if (phieu == null) {
            ThongBao.inLoi("Phieu dat phong khong hop le!");
            return;
        }
        for (PhieuDatPhong dp : lichsudatphong) {
            if (dp.getMaPhieu() != null && dp.getMaPhieu().equalsIgnoreCase(phieu.getMaPhieu())) {
                return;
            }
        }
        lichsudatphong.add(phieu);
    }

    public void setLoaikhach(String loaikhach) {
        if (loaikhach == null || loaikhach.trim().isEmpty()) {
            ThongBao.inLoi("Loai khach khong duoc de trong!");
            return;
        }
        if (loaikhach.contains(";")) {
            ThongBao.inLoi("Loai khach khong duoc chua ky tu ';'!");
            return;
        }
        this.loaikhach = loaikhach.trim();
    }

    public void setLichsudatphong(List<PhieuDatPhong> lichsudatphong) {
        if (lichsudatphong == null) {
            this.lichsudatphong = new ArrayList<>();
            return;
        }
        List<PhieuDatPhong> hopLe = new ArrayList<>();
        for (PhieuDatPhong dp : lichsudatphong) {
            if (dp != null) {
                hopLe.add(dp);
            }
        }
        this.lichsudatphong = hopLe;
    }

    public static void main(String[] args) {
        KhachHang kh = new KhachHang();
        kh.nhap();
        kh.xuat();
    }
}

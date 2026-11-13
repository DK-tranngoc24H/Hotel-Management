import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class khachHang extends nguoi {
    Scanner sc = new Scanner(System.in);
    protected String loaikhach;
    protected List<Datphong> lichsudatphong = new ArrayList<>();

    public khachHang() {
        super();
        this.loaikhach = "";
    }

    public khachHang(String hoten, String CCCD, String sdt, String diachi, String loaikhach, List<Datphong> lichsudatphong) {
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

        int n = KiemTraLoi.nhapSoNguyenDuong(sc, "Nhap so luong lich su dat phong: ");

        lichsudatphong = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap thong tin phong thu " + (i + 1) + ":");
            Datphong dp = new Datphong();
            dp.nhap();
            lichsudatphong.add(dp);
        }
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Loai khach: " + loaikhach);
        System.out.println("Lich su dat phong: ");
        if (lichsudatphong == null || lichsudatphong.isEmpty()) {
            System.out.println("  (Chua co lich su dat phong)");
        } else {
            for (Datphong dp : lichsudatphong) {
                System.out.println("  " + dp.toString());
            }
        }
    }

    public String getLoaikhach() {
        return loaikhach;
    }

    public List<Datphong> getLichsudatphong() {
        return lichsudatphong;
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

    public void setLichsudatphong(List<Datphong> lichsudatphong) {
        if (lichsudatphong == null) {
            this.lichsudatphong = new ArrayList<>();
            return;
        }
        List<Datphong> hopLe = new ArrayList<>();
        for (Datphong dp : lichsudatphong) {
            if (dp != null) {
                hopLe.add(dp);
            }
        }
        this.lichsudatphong = hopLe;
    }

    public static void main(String[] args) {
        khachHang kh = new khachHang();
        kh.nhap();
        kh.xuat();
    }
}

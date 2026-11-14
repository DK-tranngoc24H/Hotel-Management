import java.util.Scanner;

public class NhanVien extends Nguoi {
    Scanner sc = new Scanner(System.in);
    String manv;
    String chucvu;
    int luong;

    public NhanVien() {
        super();
        this.manv = "";
        this.chucvu = "";
        this.luong = 0;
    }

    public NhanVien(String hoten, String CCCD, String sdt, String diachi, String manv, String chucvu, int luong) {
        super(hoten, CCCD, sdt, diachi);
        this.manv = "";
        this.chucvu = "";
        this.luong = 0;
        setmanv(manv);
        setchucvu(chucvu);
        setLuong(luong);
    }

    public static boolean kiemTraMaNV(String manv) {
        return manv != null && manv.trim().matches("^[A-Za-z0-9_-]+$");
    }

    @Override
    public void nhap() {
        super.nhap();

        while (true) {
            manv = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma nhan vien: ");
            if (kiemTraMaNV(manv)) {
                break;
            }
            ThongBao.inLoi("Ma nhan vien chi duoc chua chu cai, so, '_' hoac '-' (khong khoang trang)!");
        }

        while (true) {
            chucvu = KiemTraLoi.nhapChuoiKhongRong(sc, "Chuc Vu: ");
            if (!chucvu.contains(";")) {
                break;
            }
            ThongBao.inLoi("Chuc vu khong duoc chua ky tu ';'!");
        }

        luong = KiemTraLoi.nhapSoNguyenDuong(sc, "Luong: ");
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println("Ma Nhan Vien: " + manv);
        System.out.println("Chuc Vu: " + chucvu);
        System.out.println("Luong: " + luong);
    }

    @Override
    public String getMaDinhDanh() {
        return manv;
    }

    public String getManv() {
        return manv;
    }

    public String getChucvu() {
        return chucvu;
    }

    public int getLuong() {
        return luong;
    }

    public void setmanv(String manv) {
        if (manv == null || manv.trim().isEmpty()) {
            ThongBao.inLoi("Ma nhan vien khong duoc de trong!");
            return;
        }
        if (!kiemTraMaNV(manv)) {
            ThongBao.inLoi("Ma nhan vien chi duoc chua chu cai, so, '_' hoac '-' (khong khoang trang)!");
            return;
        }
        this.manv = manv.trim();
    }

    public void setchucvu(String chucvu) {
        if (chucvu == null || chucvu.trim().isEmpty()) {
            ThongBao.inLoi("Chuc vu khong duoc de trong!");
            return;
        }
        if (chucvu.contains(";")) {
            ThongBao.inLoi("Chuc vu khong duoc chua ky tu ';'!");
            return;
        }
        this.chucvu = chucvu.trim();
    }

    public void setLuong(int luong) {
        if (luong < 0) {
            ThongBao.inLoi("Luong khong duoc am!");
            return;
        }
        this.luong = luong;
    }

    public static void main(String[] args) {
        NhanVien nv = new NhanVien();
        nv.nhap();
        nv.xuat();
    }
}

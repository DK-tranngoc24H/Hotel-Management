import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class KHACHHANG extends NGUOI {
    Scanner sc = new Scanner(System.in);
    protected String loaikhach;
    protected List<PhieuDatPhong> lichsudatphong = new ArrayList<>();

    public KHACHHANG() {
        super();
        this.loaikhach = "";
    }

    public KHACHHANG(String hoten, String CCCD, String sdt, String diachi, String loaikhach, List<PhieuDatPhong> lichsudatphong) {
        super(hoten, CCCD, sdt, diachi);
        this.loaikhach = loaikhach;
        this.lichsudatphong = (lichsudatphong != null) ? lichsudatphong : new ArrayList<>();
    }

    public void nhap() {
        super.nhap();

        System.out.print("Nhap loai khach: ");
        loaikhach = sc.nextLine();

        System.out.print("Nhap so luong lich su dat phong: ");
        int n = Integer.parseInt(sc.nextLine());
        lichsudatphong = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap thong tin phong thu " + (i + 1) + ":");
            PhieuDatPhong dp = new PhieuDatPhong();
            dp.nhap(sc);
            lichsudatphong.add(dp);
        }
    }

    public void xuat() {
        super.xuat();
        System.out.println("Loai khach: " + loaikhach);
        System.out.println("Lich su dat phong: ");
        if (lichsudatphong == null || lichsudatphong.isEmpty()) {
            System.out.println("  (Chua co lich su dat phong)");
        } else {
            for (PhieuDatPhong dp : lichsudatphong) {
                dp.xuat();
            }
        }
    }

    public String getLoaikhach() {
        return loaikhach;
    }

    public List<PhieuDatPhong> getLichsudatphong() {
        return lichsudatphong;
    }

    public void setLoaikhach(String loaikhach) {
        this.loaikhach = loaikhach;
    }

    public void setLichsudatphong(List<PhieuDatPhong> lichsudatphong) {
        this.lichsudatphong = lichsudatphong;
    }
    public static void main(String[]args)
    {
        KHACHHANG kh=new KHACHHANG();
        kh.nhap();
        kh.xuat();
        
    }
}

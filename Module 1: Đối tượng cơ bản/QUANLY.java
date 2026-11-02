import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class QUANLY extends NHANVIEN {
    Scanner sc = new Scanner(System.in);
    String PhongBQL;
    double phuCapChucVu;
    List<CATRUC> danhsachcatruc = new ArrayList<>();

    public QUANLY()
    {
        super();
        this.PhongBQL = "";
        this.phuCapChucVu = 0.0;
    }
    
    public QUANLY(String hoten, String CCCD, String sdt, String diachi, String manv, String chucvu, int luong)
    {
        super(hoten, CCCD, sdt, diachi, manv, chucvu, luong);
        this.PhongBQL = "";
        this.phuCapChucVu = 0.0;
        this.danhsachcatruc = new ArrayList<>();
    }

    public QUANLY(String hoten, String CCCD, String sdt, String diachi, String manv, String chucvu, int luong, String PhongBQL, double phuCapChucVu)
    {
        super(hoten, CCCD, sdt, diachi, manv, chucvu, luong);
        this.PhongBQL = PhongBQL;
        this.phuCapChucVu = phuCapChucVu;
        this.danhsachcatruc = new ArrayList<>();
    }

    @Override
    public void nhap()
    {
        super.nhap();
        System.out.print("Nhap Phong Ban Quan Ly: ");
        PhongBQL = sc.nextLine();
        System.out.print("Nhap Phu Cap Chuc Vu: ");
        try {
            phuCapChucVu = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            phuCapChucVu = 0.0;
        }

        System.out.print("Nhap so luong ca truc: ");
        int n = 0;
        try {
            n = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            n = 0;
        }

        danhsachcatruc = new ArrayList<>();
        for (int i = 0; i < n; i++)
        {
            System.out.println("Nhap Ca Truc thu " + (i + 1) + ":");
            CATRUC ct = new CATRUC();
            ct.nhap();
            danhsachcatruc.add(ct);
        }
    }

    @Override
    public void xuat()
    {
        super.xuat();
        System.out.println("Phong Ban Quan Ly: " + PhongBQL);
        System.out.println("Phu Cap Chuc Vu: " + phuCapChucVu);
        System.out.println("Danh sach ca truc:");
        if (danhsachcatruc == null || danhsachcatruc.isEmpty()) {
            System.out.println("  (Chua co ca truc nao)");
        } else {
            for (CATRUC ct : danhsachcatruc) {
                System.out.print("  ");
                ct.xuat();
            }
        }
    }

    public String getPhongBQL() {
        return PhongBQL;
    }

    public void setPhongBQL(String phongBQL) {
        this.PhongBQL = phongBQL;
    }

    public double getPhuCapChucVu() {
        return phuCapChucVu;
    }

    public void setPhuCapChucVu(double phuCapChucVu) {
        this.phuCapChucVu = phuCapChucVu;
    }

    public List<CATRUC> getDanhsachcatruc() {
        return danhsachcatruc;
    }

    public void setDanhsachcatruc(List<CATRUC> danhsachcatruc) {
        this.danhsachcatruc = danhsachcatruc;
    }
}

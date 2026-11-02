import java.util.Scanner;

public class CATRUC {
    Scanner sc = new Scanner(System.in);
    private String maCa;
    private String tenCa;
    private String gioBatDau;
    private String gioKetThuc;
    private String ngayTruc;

    public CATRUC()
    {
        this.maCa = "";
        this.tenCa = "";
        this.gioBatDau = "";
        this.gioKetThuc = "";
        this.ngayTruc = "";
    }

    public CATRUC(String maCa, String tenCa, String gioBatDau, String gioKetThuc, String ngayTruc)
    {
        this.maCa = maCa;
        this.tenCa = tenCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngayTruc = ngayTruc;
    }

    public void nhap()
    {
        System.out.print("Nhap ma ca: ");
        this.maCa = sc.nextLine();
        System.out.print("Nhap ten ca (Ca sang/chieu/toi): ");
        this.tenCa = sc.nextLine();
        System.out.print("Nhap gio bat dau: ");
        this.gioBatDau = sc.nextLine();
        System.out.print("Nhap gio ket thuc: ");
        this.gioKetThuc = sc.nextLine();
        System.out.print("Nhap ngay truc: ");
        this.ngayTruc = sc.nextLine();
    }

    public void xuat()
    {
        System.out.println("Ma Ca: " + maCa + " | Ten Ca: " + tenCa + " | Thoi Gian: " + gioBatDau + " - " + gioKetThuc + " | Ngay Truc: " + ngayTruc);
    }

    public String getMaCa()
    {
        return maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public String getNgayTruc() {
        return ngayTruc;
    }

    public void setmaCa(String maCa)
    {
        this.maCa = maCa;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public void setNgayTruc(String ngayTruc) {
        this.ngayTruc = ngayTruc;
    }
}

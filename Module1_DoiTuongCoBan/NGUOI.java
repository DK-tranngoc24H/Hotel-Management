import java.util.Scanner;

public abstract class NGUOI
{
    Scanner sc = new Scanner(System.in);
    protected String hoten;
    protected String CCCD;
    protected String sdt;
    protected String diachi;

    // Thanh phan static dem so luong nguoi da duoc tao (Buoc 5)
    public static int soLuongNguoi = 0;

    public NGUOI()
    {
        this.hoten = "";
        this.CCCD = "";
        this.sdt = "";
        this.diachi = "";
        soLuongNguoi++;
    }

    public NGUOI(String hoten, String CCCD, String sdt, String diachi)
    {
        this.hoten = hoten;
        this.CCCD = CCCD;
        this.sdt = sdt;
        this.diachi = diachi;
        soLuongNguoi++;
    }

    public void nhap()
    {
        System.out.print("Nhap Ho va Ten: ");
        hoten = sc.nextLine();
        System.out.print("Nhap CCCD: ");
        CCCD = sc.nextLine();
        System.out.print("Nhap sdt: ");
        sdt = sc.nextLine();
        System.out.print("Nhap Dia Chi: ");
        diachi = sc.nextLine();
    }

    public void xuat()
    {
        System.out.println("Ho va Ten: " + hoten + " | CCCD: " + CCCD + " | SDT: " + sdt + " | Dia Chi: " + diachi);
    }

    // Phuong thuc truu tuong hoac da hinh tra ve ma dinh danh (CCCD hoac MaNV)
    public String getMaDinhDanh() {
        return CCCD;
    }

    public String gethoten() {
        return hoten;
    }
    public String getCCCD()
    {
        return CCCD;
    }
    public String getSdt() {
        return sdt;
    }
    public String getDiachi() {
        return diachi;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public void setCCCD(String cCCD) {
        this.CCCD = cCCD;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}

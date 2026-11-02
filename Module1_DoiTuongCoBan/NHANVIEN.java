import java.util.Scanner;

public class NHANVIEN extends NGUOI{
    Scanner sc=new Scanner(System.in);
    String manv;
    String chucvu;
    int luong;
    
    public NHANVIEN()
    {
        super();
        this.manv="";
        this.chucvu="";
        this.luong=0;
    }
    public NHANVIEN(String hoten,String CCCD,String sdt,String diachi,String manv,String chucvu,int luong )
    {
        super(hoten,CCCD,sdt,diachi);
        this.manv=manv;
        this.chucvu=chucvu;
        this.luong=luong;
    }
    public void nhap()
    {
        super.nhap();

        System.out.print("Nhap ma nhan vien: ");
        manv=sc.nextLine();
        System.out.print("Chuc Vu: ");
        chucvu=sc.nextLine();
        System.out.print("Luong: ");
        luong=Integer.parseInt(sc.nextLine());
    }
    public void xuat()
    {
        super.xuat();
        System.out.println("Ma Nhan Vien: "+manv);
        System.out.println("Chuc Vu: "+chucvu);
        System.out.println("Luong: "+luong);
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
    public void setmanv(String manv)
    {
        this.manv=manv;
    }
    public void  setchucvu(String chucvu)
    {
        this.chucvu=chucvu;
    }
    public void setLuong(int luong) {
        this.luong = luong;
    }
    public static void main(String[]args)
    {
        NHANVIEN nv=new NHANVIEN();
        nv.nhap();
        nv.xuat();
    }
}

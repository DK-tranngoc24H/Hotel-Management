import java.util.Scanner;

public class ThongTinKhachHang {

  private String hoTen;
  private int age;
  private String diaChi;

  public ThongTinKhachHang()
  {
    hoTen = null;
    age = 0;
    diaChi = null;
  }

  public ThongTinKhachHang(String hoTen, int age, String diaChi)
  {
    this.hoTen = hoTen;
    this.age = age;
    this.diaChi = diaChi;

  }

  /**
   * Nhập thông tin khách hàng từ Scanner được truyền vào.
   * Dùng Scanner chung từ lớp gọi để tránh xung đột System.in.
   *
   * @param sc Scanner đang dùng của lớp gọi
   */
  public void nhapThongTin(Scanner sc) {
    System.out.print("Nhap ho va ten khach: ");
    this.hoTen = sc.nextLine();

    System.out.print("Nhap tuoi khach: ");
    while (!sc.hasNextInt()) {
      System.out.print("Tuoi khong hop le, nhap lai: ");
      sc.nextLine();
    }
    this.age = sc.nextInt();
    sc.nextLine(); // Xoa ky tu xuong dong con lai

    System.out.print("Nhap dia chi khach: ");
    this.diaChi = sc.nextLine();
  }

  public void xuatThongTin()
  {
    System.out.println("Ho va ten khach: " + hoTen);
    System.out.println("Tuoi cua khach: "+ age);
    System.out.println("Dia chi cua khach: "+ diaChi);
  }

  public void setTen(Scanner sc) {
    System.out.print("Nhap ho va ten moi: ");
    hoTen = sc.nextLine();
  }

  public void setTen(String hoTen)
  {
    this.hoTen = hoTen;
  }

  public String getTen()
  {
    return this.hoTen;
  }

  public void setAge(Scanner sc) {
    System.out.print("Nhap tuoi moi: ");
    age = sc.nextInt();
    sc.nextLine();
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public int getAge()
  {
    return this.age;
  }

  public void setDiaChi(Scanner sc) {
    System.out.print("Nhap dia chi moi: ");
    diaChi = sc.nextLine();
  }

  public void setDiaChi(String diaChi)
  {
    this.diaChi = diaChi;
  }

  public String getDiaChi()
  {
    return this.diaChi;
  }



  
}

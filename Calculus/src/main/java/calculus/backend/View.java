package calculus.backend;

public class View {
    //esto define una jerarquia
    public interface All{}
    public interface UserSummary extends  All{}
    public interface UserExtended extends UserSummary{}
    public interface AdminSummary extends UserExtended{}
    public interface AdminExtended extends AdminSummary{}
    
}
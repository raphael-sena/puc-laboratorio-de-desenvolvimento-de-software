import models.Aluno;
import models.Secretaria;
import models.Usuario;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.print("Hello and welcome!");

        Usuario usuario = new Usuario();
        usuario.setNome("Raphael");
        usuario.setSenha("123456");
        System.out.println("\n"+usuario);

        Aluno aluno = new Aluno();
        aluno.setNome("Raphael");
        aluno.setSenha("123456");
        aluno.setMatricula("1234567");
        System.out.println(aluno);

        Secretaria secretaria = new Secretaria();
        secretaria.setNome("ICEI");
        secretaria.setSenha("123456");
        System.out.println(secretaria);
    }
}
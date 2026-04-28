package br.ufal.ic.myfood;

import easyaccept.EasyAccept;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String base = findTestBase();
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us1_1.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us1_2.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us2_1.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us2_2.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us3_1.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us3_2.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us4_1.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us4_2.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us5_1.txt"});
        EasyAccept.main(new String[] {"br.ufal.ic.myfood.Facade", base + "us5_2.txt"});
    }

    private static String findTestBase() {
        // (MyFood2026.1/)
        if (new File("tests/us1_1.txt").exists()) return "tests/";
        // raiz do repo 
        if (new File("MyFood2026.1/tests/us1_1.txt").exists()) return "MyFood2026.1/tests/";
        // pasta extraída do zip
        File[] subdirs = new File(".").listFiles(File::isDirectory);
        if (subdirs != null) {
            for (File sub : subdirs) {
                String path = sub.getName() + "/MyFood2026.1/tests/";
                if (new File(path + "us1_1.txt").exists()) return path;
            }
        }
        throw new IllegalStateException(
                "Nao foi possivel localizar a pasta de testes. Diretorio atual: "
                        + new File(".").getAbsolutePath()
        );
    }
}

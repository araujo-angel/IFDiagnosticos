package validators;

import model.Exame;
import model.Ressonancia;

public class ValidadorRessonancia implements ValidadorExame {
    @Override
    public boolean validar(Exame exame) {
        if (!(exame instanceof Ressonancia)) {
            return false;
        }
        Ressonancia ressonancia = (Ressonancia) exame;
        return ressonancia.getAreaCorpo() != null && !ressonancia.getAreaCorpo().isEmpty();
    }
}
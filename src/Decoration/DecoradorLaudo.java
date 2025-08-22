package Decoration;

import template.LaudoTemplate;

public abstract class DecoradorLaudo implements LaudoTemplate {
    protected final LaudoTemplate laudo;

    public DecoradorLaudo(LaudoTemplate laudo) {
        this.laudo = laudo;
    }
}
package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

public class SVGIcon {
    public static SVGPath getIcon(String type, String color) {
        SVGPath icon = new SVGPath();
        switch (type) {
            case "Editar":
                icon.setContent("m19.3 8.925l-4.25-4.2l1.4-1.4q.575-.575 1.413-.575t1.412.575l1.4 1.4q.575.575.6 1.388t-.55 1.387L19.3 8.925ZM17.85 10.4L7.25 21H3v-4.25l10.6-10.6l4.25 4.25Z");
                break;
            case "Excluir":
                icon.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
                break;
            case "Adicionar":
                icon.setContent("M6.5 1.75a.75.75 0 0 0-1.5 0V5H1.75a.75.75 0 0 0 0 1.5H5v3.25a.75.75 0 0 0 1.5 0V6.5h3.25a.75.75 0 0 0 0-1.5H6.5V1.75Z");
                break;
            case "Mais":
                icon.setContent("M2.5 7.5a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Zm15 0a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Zm-7.274 0a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Z");
                break;
            case "Play":
                icon.setContent("m11.596 8.697l-6.363 3.692c-.54.313-1.233-.066-1.233-.697V4.308c0-.63.692-1.01 1.233-.696l6.363 3.692a.802.802 0 0 1 0 1.393z");
                break;
            case "Pause":
                icon.setContent("M14 19h4V5h-4M6 19h4V5H6v14Z");
                break;
            case "Fullscreen":
                icon.setContent("M5 19v-5h2v3h3v2H5Zm0-9V5h5v2H7v3H5Zm9 9v-2h3v-3h2v5h-5Zm3-9V7h-3V5h5v5h-2Z");
                break;
            default:
                throw new AssertionError();
        }
        
        icon.setFill(Color.web(color));
        icon.autosize();
        return icon;
    }
}

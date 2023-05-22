package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

public class SVGIcon {
    public static SVGPath getIcon(String type, String color) {
        SVGPath icon = new SVGPath();
        switch (type) {
            case "Editar":
//                icon.setContent("M7 7H6a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2-2v-1");
                icon.setContent("m19.3 8.925l-4.25-4.2l1.4-1.4q.575-.575 1.413-.575t1.412.575l1.4 1.4q.575.575.6 1.388t-.55 1.387L19.3 8.925ZM17.85 10.4L7.25 21H3v-4.25l10.6-10.6l4.25 4.25Z");
//                icon.setContent("M7 21q-.825 0-1.413-.588T5 19V6H4V4h5V3h6v1h5v2h-1v13q0 .825-.588 1.413T17 21H7ZM17 6H7v13h10V6ZM9 17h2V8H9v9Zm4 0h2V8h-2v9ZM7 6v13V6Z");
                break;
            case "Excluir":
//                icon.setContent("M7 21q-.825 0-1.413-.588T5 19V6H4V4h5V3h6v1h5v2h-1v13q0 .825-.588 1.413T17 21H7ZM17 6H7v13h10V6ZM9 17h2V8H9v9Zm4 0h2V8h-2v9ZM7 6v13V6Z");
                icon.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
                break;
            case "Adicionar":
//                icon.setContent("M24 16v16m-8-8h16");
                icon.setContent("M6.5 1.75a.75.75 0 0 0-1.5 0V5H1.75a.75.75 0 0 0 0 1.5H5v3.25a.75.75 0 0 0 1.5 0V6.5h3.25a.75.75 0 0 0 0-1.5H6.5V1.75Z");
                break;
            case "Mais":
//                icon.setContent("M0 5a3 3 0 0 1 3-3h18a3 3 0 0 1 3 3v14a3 3 0 0 1-3 3H3a3 3 0 0 1-3-3V5Zm3-1h18a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1Z");
                icon.setContent("M2.5 7.5a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Zm15 0a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Zm-7.274 0a2.5 2.5 0 1 1 0 5a2.5 2.5 0 0 1 0-5Z");
                break;
            default:
                throw new AssertionError();
        }
        
        icon.setFill(Color.web(color));
        icon.autosize();
        return icon;
    }
}

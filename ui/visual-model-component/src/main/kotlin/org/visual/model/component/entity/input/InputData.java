package org.visual.model.component.entity.input;

import java.util.Set;
import javafx.scene.input.MouseButton;

public class InputData {
    public boolean ctrl;
    public boolean alt;
    public boolean shift;
    public Key key;

//    public static final Rule<InputData> rule = new ObjectRule<>(InputData::new)
//        .put("ctrl", (o, it) -> o.ctrl = it, BoolRule.get())
//        .put("alt", (o, it) -> o.alt = it, BoolRule.get())
//        .put("shift", (o, it) -> o.shift = it, BoolRule.get())
//        .put("key", (o, it) -> o.key = new Key(it), StringRule.get());

    public InputData() {
    }

    public InputData(InputData data) {
        this.ctrl = data.ctrl;
        this.alt = data.alt;
        this.shift = data.shift;
        this.key = data.key;
    }

    public InputData(boolean ctrl, boolean alt, boolean shift, Key key) {
        this.ctrl = ctrl;
        this.alt = alt;
        this.shift = shift;
        this.key = key;
    }

    public InputData(Key key) {
        this(false, false, false, key);
    }

    public boolean matches(Set<KeyCode> keys, Set<MouseButton> buttons, KeyCode currentKey, MouseButton currentMouse) {
        if (ctrl) {
            if (!keys.contains(KeyCode.CONTROL)) return false;
        }
        if (alt) {
            if (!keys.contains(KeyCode.ALT)) return false;
        }
        if (shift) {
            if (!keys.contains(KeyCode.SHIFT)) return false;
        }
//        if (key.key != null) return key.key == currentKey;
//        if (key.button != null) return key.button == currentMouse;
//        if (currentKey == KeyCode.CONTROL) return ctrl;
//        if (currentKey == KeyCode.ALT) return alt;
//        if (currentKey == KeyCode.SHIFT) return shift;
        return false;
    }

}

// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.actions;

import static org.openstreetmap.josm.gui.help.HelpUtil.ht;
import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;
import org.openstreetmap.josm.tools.Shortcut;

/**
 * Redoes the last command.
 *
 * @author imi
 */
public class RedoAction extends JosmAction implements OsmDataLayer.CommandQueueListener {

    /**
     * Construct the action with "Redo" as label.
     */
    public RedoAction() {
        super(tr("Redo"), "redo", tr("Redo the last undone action."),
                Shortcut.registerShortcut("system:redo", tr("Edit: {0}", tr("Redo")), KeyEvent.VK_Y, Shortcut.CTRL), true);
        setEnabled(false);
        putValue("help", ht("/Action/Redo"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MapFrame map = MainApplication.getMap();
        if (map == null)
            return;
        map.repaint();
        MainApplication.undoRedo.redo();
    }

    @Override
    protected void updateEnabledState() {
        setEnabled(Main.main != null && !MainApplication.undoRedo.redoCommands.isEmpty());
    }

    @Override
    public void commandChanged(int queueSize, int redoSize) {
        if (MainApplication.undoRedo.redoCommands.isEmpty()) {
            putValue(NAME, tr("Redo"));
            setTooltip(tr("Redo the last undone action."));
        } else {
            putValue(NAME, tr("Redo ..."));
            setTooltip(tr("Redo {0}",
                    MainApplication.undoRedo.redoCommands.getFirst().getDescriptionText()));
        }
    }
}

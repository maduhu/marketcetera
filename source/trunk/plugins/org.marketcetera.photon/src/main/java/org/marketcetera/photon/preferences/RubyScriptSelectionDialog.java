package org.marketcetera.photon.preferences;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.marketcetera.photon.PhotonPlugin;


/**
 * Dialog that displays workspace tree filtered down to Ruby scripts and containers (projects, folders)
 * and allows to pick a single script.
 * 
 * @author andrei@lissovski.org
 */
public class RubyScriptSelectionDialog extends ElementTreeSelectionDialog {

	public RubyScriptSelectionDialog(Shell parent) {
		super(parent, new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		setTitle("Ruby script selection");
		setMessage("Choose a Ruby script to be registered for the event");
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		addFilter(new TreeElementFilter());
		setValidator(new SelectionValidator());
		setAllowMultiple(false);
	}

	private static boolean isRubyFile(IFile file) {
		return file.getName().toLowerCase().endsWith(".rb");  //$NON-NLS-1$
	}		

	private static class TreeElementFilter extends ViewerFilter {
		public boolean select(Viewer viewer, Object parent, Object element) {
			if (element instanceof IProject) {
				return true;
			}
			else if (element instanceof IFolder) {
				return true;
			}
			else if (element instanceof IFile) {
				return isRubyFile((IFile) element);
			}
			
			return false;
		}
	}
	
	private static class SelectionValidator implements ISelectionStatusValidator {

		public IStatus validate(Object[] selection) {
			if (selection.length == 1 && selection[0] instanceof IFile) {
				IFile file = (IFile) selection[0];
				if (isRubyFile(file)) {
					return new Status(
							IStatus.OK,
							PhotonPlugin.ID,
							IStatus.OK,
							"", //$NON-NLS-1$
							null);
				}
			}
			
			return new Status(
					IStatus.ERROR,
					PhotonPlugin.ID,
					IStatus.ERROR,
					"", //$NON-NLS-1$
					null);
		}
	}

}
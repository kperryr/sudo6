package pkgGame;

import pkgEnum.eAction;

public class Action {

	private eAction Action;
	private Cell ActionCell;
	
	public Action(eAction action, Cell actionCell) {
		super();
		Action = action;
		ActionCell = actionCell;
	}
	public eAction getAction() {
		return Action;
	}
	public Cell getActionCell() {
		return ActionCell;
	}	
}

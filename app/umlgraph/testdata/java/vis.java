// $Id: vis.java,v 1.1 2005/11/23 22:18:45 dds Exp $

/**
 * Attribute and operation visility
 * UML User Guide p. 123
 *
 * @opt operations 
 * @opt attributes 
 * @opt types 
 * @opt visibility 
 * @hidden
 */
class UMLOptions {}

/** @hidden */
class Tool {}

class Toolbar {
	protected Tool currentSelection;
	protected Integer toolCount;
	public void pickItem(Integer i) {}
	public void addTool(Tool t) {}
	public void removeTool(Integer i) {}
	public Tool getTool() {}
	protected void checkOrphans() {}
	private void compact() {}
}

/**
 * Created by doug on 10/24/16
 */
public abstract class AbstractCard {

	private AbstractCard child = null;
	private AbstractCard parent = null;

	public AbstractCard setParent(AbstractCard parent){
		if(this.parent == null || !this.parent.equals(parent)){
			this.parent = parent;
			if(parent != null) {
				parent.setChild(this);
			}
		}

		// return the parent every time
		return parent;
	}

	public AbstractCard setChild(AbstractCard child){
		if(this.child == null || !this.child.equals(child)){
			this.child = child;
			if(child != null) {
				child.setParent(this);
			}
		}

		// return the child every time
		return child;
	}

	public AbstractCard getChild() {
		return child;
	}

	public AbstractCard getParent() {
		return parent;
	}

	public AbstractCard getRoot(){
		if(parent != null){
			return parent.getRoot();
		} else {
			return this;
		}
	}

	public AbstractCard getEnd() {
		if(child == null){
			return this;
		} else {
			return child.getEnd();
		}
	}

	public AbstractCard getNthChild(int x){
		if(x == 0){
			return this;
		} if (child == null){
			return null;
		} else {
			return child.getNthChild(x-1);
		}
	}

	public int size() {
		if(child == null){
			return 1;
		} else {
			return 1 + child.size();
		}
	}

	public abstract AbstractCard copy();
}


public interface Set<E> extends Iterable<E>{
	
	public boolean add(E newEntry);
	public boolean remove(E element);
	public boolean isMember(E element);
	public int size();
	public boolean isEmpty();
	public void clear();
	public Set<E> union(Set<E> S2);
	public Set<E> intersection(Set<E> S2);
	public Set<E> difference(Set<E> S2);
	public boolean isSubset(Set<E> S2);
	

}

package fr.unice.polytech.devops.groupe7.mutagen.mutation;

/** tests all contracts of a class T based on a single instance */
public interface TestDriver<T> {
	/** throws an AssertionError is one contract is violated */
	void test(T t); 
}

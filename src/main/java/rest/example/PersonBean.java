package rest.example;

import rest.fuyou.Term;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PersonBean {

	private String name;
	private int age;
	private List<Term> terms;

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	public void addTerm(Term term) {
		if (terms == null) {
			terms = new LinkedList<Term>();
		}
		terms.add(term);
	}
	public PersonBean() {
	}

	public PersonBean(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonBean[name='" + name + "',age='" + age +"',terms="+terms+ "']";
	}
}
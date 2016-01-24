package hok.chompzki.hivetera.client.book;

import hok.chompzki.hivetera.client.book.tokens.Token;

import java.util.ArrayDeque;
import java.util.Deque;

public class TokenCollection {
	public String name = "";
	public Deque<Token> stack = new ArrayDeque<Token>();
}

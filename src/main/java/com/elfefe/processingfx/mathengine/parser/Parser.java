package com.elfefe.processingfx.mathengine.parser;

public interface Parser<T, R> {
    R parse(T t);
}

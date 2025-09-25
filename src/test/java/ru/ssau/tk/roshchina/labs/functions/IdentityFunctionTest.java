package ru.ssau.tk.roshchina.labs.functions;

import static org.junit.jupiter.api.Assertions.*;
class IdentityFunctionTest {
    void testApply() {
        IdentityFunction identityFunction = new IdentityFunction();
        assertEquals(0.0, identityFunction.apply(0.0), 0.0001);
        assertEquals(3.0, identityFunction.apply(3.0), 0.0001);
        assertEquals(-1.7, identityFunction.apply(-1.7), 0.0001);
        assertEquals(300.0, identityFunction.apply(300.0), 0.0001);
        assertEquals(-300.0, identityFunction.apply(-300.0), 0.0001);
        assertEquals(5.3679, identityFunction.apply(5.3679), 0.0001);
    }
}
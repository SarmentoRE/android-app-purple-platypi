package com.cmsc355.contactapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Relation.class)
public class RelationTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testGettersAndSetters() throws Exception {
        Relation testRelation = new Relation(0,0);
        testRelation.setContactId(5);
        testRelation.setGroupId(10);
        assertEquals(testRelation.getContactId(), 5);
        assertEquals(testRelation.getGroupId(), 10);
    }


    @Test
    public void testParameterizedConstructor() throws Exception {
        Relation testRelation = new Relation(10,20);
        assertEquals(testRelation.getContactId(), 10);
        assertEquals(testRelation.getGroupId(), 20);
    }

}

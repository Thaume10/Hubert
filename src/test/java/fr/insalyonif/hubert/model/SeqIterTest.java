package fr.insalyonif.hubert.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SeqIterTest {

    private SeqIter seqIter;
    private Graph mockGraph;
    private List<Integer> unvisited;

    @BeforeEach
    void setUp() {
        mockGraph = mock(Graph.class);
        unvisited = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        int currentVertex = 1;

        // Setup the mock graph behavior
        when(mockGraph.isArc(eq(currentVertex), anyInt())).thenAnswer(invocation -> {
            Integer destination = invocation.getArgument(1);
            // Define logic for determining if an arc exists
            return destination % 2 == 0; // for example, only even numbered vertices
        });

        seqIter = new SeqIter(unvisited, currentVertex, mockGraph);
    }

    @Test
    void testHasNextWhenNotEmpty() {
        assertTrue(seqIter.hasNext(), "hasNext should return true when there are candidates");
    }

    @Test
    void testNext() {
        assertTrue(Arrays.asList(2, 4).contains(seqIter.next()), "next should return the next candidate");
        assertTrue(seqIter.hasNext(), "hasNext should still return true after fetching a candidate");
    }

    @Test
    void testIterationCompleteness() {
        List<Integer> fetchedCandidates = new ArrayList<>();
        while (seqIter.hasNext()) {
            fetchedCandidates.add(seqIter.next());
        }

        List<Integer> expectedCandidates = Arrays.asList(4, 2); // Based on the mock graph's arc existence logic
        assertEquals(expectedCandidates, fetchedCandidates, "The iteration did not fetch all valid candidates");
    }

    @Test
    void testHasNextWhenEmpty() {
        while (seqIter.hasNext()) {
            seqIter.next();
        }
        assertFalse(seqIter.hasNext(), "hasNext should return false when there are no more candidates");
    }

    @Test
    void testConstructorAndIteration() {
        List<Integer> iteratedVertices = new ArrayList<>();
        while (seqIter.hasNext()) {
            iteratedVertices.add(seqIter.next());
        }

        // Assuming only even numbered vertices are connected from the current vertex
        List<Integer> expectedVertices = Arrays.asList(4, 2);
        assertEquals(expectedVertices, iteratedVertices, "Iterator should iterate over the correct vertices");
    }

    @Test
    void testHasNext() {
        assertTrue(seqIter.hasNext(), "hasNext should return true if there are more elements to iterate");
        seqIter.next(); // Consuming one element
        assertTrue(seqIter.hasNext(), "hasNext should still return true if there are more elements after one is consumed");
    }

    @Test
    void testNextWithNoMoreElements() {
        // Consume all elements
        while (seqIter.hasNext()) {
            seqIter.next();
        }

        // Now the iterator should have no more elements
        assertFalse(seqIter.hasNext(), "hasNext should return false when no more elements are left");
        // Testing behavior when calling next() with no more elements is undefined and depends on your implementation
    }
}
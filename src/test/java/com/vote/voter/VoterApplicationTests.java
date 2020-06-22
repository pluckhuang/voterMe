package com.vote.voter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;
import com.google.common.primitives.Ints;
import com.vote.voter.model.Vote;
import com.vote.voter.service.ItemRepository;
import com.vote.voter.service.VoteRepository;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VoterApplicationTests {

    @Autowired
    private ItemRepository item;

    // mockmvc
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteRepository voteRepository;

    @Test
    public void contextLoads() {
        assertThat(item).isNotNull();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testVoteService() {

        Long userId = 1L;
        Long questId = 1L;
        Long itemId = 1L;

        Vote user = new Vote(userId, questId, itemId);
        when(this.voteRepository.save(user)).thenReturn(user);
        // assertThat(user).isNotNull();
    }

    @Test
    public void guavaTest() {
        Optional<Object> possible = Optional.fromNullable(null);

        assertThat(possible.isPresent()).isFalse();
        // assertThat(possible.get()).isEqualTo(0);
        int i = 2;
        checkArgument(i >= 0, "Argument was %s but expected nonnegative", i);

        checkNotNull("123", "aohh, a null pointer");

        // count
        SortedMultiset<String> mutiset = TreeMultiset.create();
        String[] words = { "jiang", "bo", "huang", "huang" };
        for (String word : words) {
            mutiset.add(word);
        }

        List<String> duplicates = Lists.newArrayList();
        for (Multiset.Entry<String> entry : mutiset.entrySet()) {
            if (entry.getCount() > 1) {
                duplicates.add(entry.getElement());
            }
        }
        assertThat(mutiset.firstEntry().getElement()).isEqualTo("bo");
        assertThat(duplicates.get(0)).isEqualTo("huang");
        assertThat(mutiset.count("huang")).isEqualTo(2);
        assertThat(mutiset.elementSet().size()).isEqualTo(3);

        ListMultimap<String, Integer> treeListMultimap = MultimapBuilder.treeKeys().arrayListValues().build();

        int j = 0;
        while (j < 10) {
            treeListMultimap.put(String.valueOf(j), j);
            treeListMultimap.put(String.valueOf(j), j + 1);
            j++;
        }
        assertThat(treeListMultimap.keys().count("1")).isEqualTo(2);

        BiMap<String, Integer> userToId = HashBiMap.create();

        String[] userNames = { "huang", "jiang", "bo" };
        int[] userIDs = { 1, 2, 3 };
        for (i = 0; i < userIDs.length; i++) {
            userToId.put(userNames[i], userIDs[i]);
        }
        assertThat(userToId.inverse().get(2)).isEqualTo("jiang");

        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
        rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
        rangeSet.add(Range.closedOpen(15, 20)); // connected range; {[1, 10], [11, 20)}
        rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
        rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
        assertThat(rangeSet.contains(6)).isFalse();
        assertThat(rangeSet.rangeContaining(4)).isEqualTo(Range.closed(1, 5));

        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
        // rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar",
        // [6, 10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => //
                                                 // "foo"}
        rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
        assertThat(rangeMap.get(4)).isEqualTo("foo");

        Set<String> copySet = Sets.newHashSet("hahaha");
        assertThat(copySet.contains("hahaha")).isTrue();

        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}
        List<List<Integer>> parts = Lists.partition(countUp, 2); // {{1, 2}, {3, 4}, {5}}

        List<String> strings = Lists.newArrayList("huang", "jiangbo", "goodboy");
        ImmutableListMultimap<Integer, String> stringsByIndex = Multimaps.index(strings,
                new Function<String, Integer>() {
                    public Integer apply(String string) {
                        return string.length();
                    }
                });
        assertThat(stringsByIndex.get(5).get(0)).isEqualTo("huang");

        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);

        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);

        // returns true: all unique elements are contained,
        assertThat(multiset1.containsAll(multiset2)).isTrue();
        // even though multiset1.count("a") == 2 < multiset2.count("a") == 5

        assertThat(multiset1.count("a")).isEqualTo(2);

        // returns false
        assertThat(Multisets.containsOccurrences(multiset2, multiset1)).isTrue();

        // multiset2.removeOccurrences(multiset1); // multiset2 now contains 3
        // occurrences of "a"
        // removes all occurrences of "a" from multiset2, even though
        // multiset1.count("a") == 2
        multiset2.removeAll(multiset1);
        assertThat(multiset2.isEmpty()).isTrue();
        // returns true

    }
}

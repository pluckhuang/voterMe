package com.vote.voter.handler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import com.vote.voter.jsonMapper.VoteFormUnit;
import com.vote.voter.model.Item;
import com.vote.voter.model.Quest;
import com.vote.voter.model.Vote;
import com.vote.voter.service.ItemRepository;
import com.vote.voter.service.QuestRepository;
import com.vote.voter.service.UserRepository;
import com.vote.voter.service.VoteRepository;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/voter")
public class QuestHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuestHandler.class);

    @Autowired
    private Environment env;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping(path = "csrftoken") // Map ONLY GET Requests
    public @ResponseBody String getCsrfToken() {
        return "ok";
    }

    @GetMapping(path = "/play") // Map ONLY GET Requests
    @JsonAnyGetter
    public @ResponseBody Map<String, Object> getVoterPlay() {

        List<Object> ret = getCurrentVote();

        Map<String, Object> res = Maps.newHashMap();

        res.put("data", ret);
        res.put("code", "0");

        return res;
    }

    @PostMapping(path = "/play")
    @JsonAnyGetter
    @Transactional
    public @ResponseBody Map<String, Object> postVoterPlay(@RequestParam String form, Principal principal) {

        String currentUsername = getCurrentUsername(principal);

        long userId = getUserIdByUserName(currentUsername);

        Map<String, Object> res = getRes(currentUsername, userId);

        if (isVoted(userId) == true) {
            res.put("code", "-3");
            return res;
        }

        if (saveVote(userId, form) != 0) {
            res.put("code", "-2");
            return res;
        }

        logger.info("play a vote here.");
        return res;
    }

    private String getCurrentUsername(Principal principal) {
        return (principal != null) ? principal.getName() : "";
    }

    private Map<String, Object> getRes(String currentUsername, Long userId) {

        Map<String, Object> res = Maps.newHashMap();

        res.put("username", currentUsername);
        res.put("userId", userId);
        res.put("code", "0");

        return res;
    }

    @GetMapping(path = "/display")
    @JsonAnyGetter
    public @ResponseBody Map<String, Object> voterDisplay(Principal principal) throws ParseException {

        Map<String, Object> res = Maps.newHashMap();

        if (isVoted(getUserIdByUserName(getCurrentUsername(principal)))) {
            res.put("code", "-3");
            res.put("data", getCurrentVote());
            return res;
        }

        Iterable<Quest> _questList = questRepository.findAll();

        HashSet<Long> questIds = Sets.newHashSet();

        for (Quest quest : _questList) {
            questIds.add(quest.getId());
        }

        ListMultimap<Long, Item> itemMutil = getDisplayItemsByQuestIds(questIds);

        ArrayList<Object> questList = Lists.newArrayList();

        for (Quest quest : _questList) {

            Map<String, Object> questMap = Maps.newHashMap();

            questMap.put("id", quest.getId());
            questMap.put("title", quest.getTitle());
            questMap.put("validSec", quest.getValidSec());
            questMap.put("createTime", quest.getCreateTime());
            questMap.put("itemList", itemMutil.get(quest.getId()));
            questList.add(questMap);
        }

        // RestTemplate restTemplate = new RestTemplate();

        // String url = env.getProperty("getUrl");
        // checkNotNull(url, "url, null pointer");

        // String getReq = restTemplate.getForObject(url, String.class);
        // Object obj = new JSONParser().parse(getReq);
        // // typecasting obj to JSONObject
        // JSONObject jo = (JSONObject) obj;

        // res.put("getReq", jo);
        res.put("code", "0");
        res.put("data", questList);

        return res;
    }

    private ListMultimap<Long, Item> getDisplayItemsByQuestIds(HashSet<Long> questIds) {

        if (questIds.isEmpty()) {
            return null;
        }
        ListMultimap<Long, Item> itemRes = MultimapBuilder.treeKeys().arrayListValues().build();

        Iterable<Item> _itemsList = itemRepository.GetItemsByQuestId(questIds);

        for (Item item : _itemsList) {
            itemRes.put(item.getQuestId(), item);
        }
        return itemRes;
    }

    private long getUserIdByUserName(String username) {

        return userRepository.getUserIdByName(username);
    }

    private int saveVote(Long userId, String form) {

        int ret = 0;
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<VoteFormUnit> myObjects = Arrays.asList(mapper.readValue(form, VoteFormUnit[].class));
            List<Vote> voteList = Lists.newArrayList();

            for (VoteFormUnit vote : myObjects) {
                Long questId = Long.parseLong(vote.getName(), 10);
                Long itemId = Long.parseLong(vote.getValue(), 10);
                Vote voteInfo = new Vote(userId, questId, itemId);
                voteList.add(voteInfo);
            }

            voteRepository.saveAll(voteList);
        } catch (Exception e) {
            ret = -2;
        }

        return ret;
    }

    private List<Object> getCurrentVote() {

        Boolean enabled = true;

        List<Vote> voteList = voteRepository.getVotes(enabled);

        // Map<Long, Integer> voteResult = Maps.newHashMap();

        SortedMultiset<Long> voteResult = TreeMultiset.create();

        HashSet<Long> questIds = Sets.newHashSet();

        if (voteList != null) {
            for (Vote vote : voteList) {
                voteResult.add(vote.getItemId());
                questIds.add(vote.getQuestId());
            }
        }

        return getDisplayList(voteResult, questIds);
    }

    private List<Object> getDisplayList(Multiset<Long> voteResult, HashSet<Long> questIds) {

        Iterable<Quest> _questList = questRepository.findAllById(questIds);

        ListMultimap<Long, Item> itemMutil = this.getDisplayItemsByQuestIds(questIds);

        List<Object> displayList = Lists.newArrayList();

        if (_questList != null) {

            for (Quest quest : _questList) {

                Map<String, Object> questMap = Maps.newHashMap();
                questMap.put("id", quest.getId());
                questMap.put("title", quest.getTitle());

                List<Object> itemList = Lists.newArrayList();
                for (Item item : itemMutil.get(quest.getId())) {

                    Map<String, Object> itemMap = Maps.newHashMap();
                    itemMap.put("itemId", item.getId().toString());
                    itemMap.put("itemContent", item.getContent());
                    itemMap.put("voteCount", voteResult.count(item.getId()));
                    itemList.add(itemMap);
                }
                questMap.put("itemList", itemList);
                displayList.add(questMap);
            }
        }
        return displayList;
    }

    private Boolean isVoted(Long userId) {
        return voteRepository.isVoted(userId);
    }

}
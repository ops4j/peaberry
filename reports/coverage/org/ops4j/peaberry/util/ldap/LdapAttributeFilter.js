var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":615,"sl":35,"methods":[{"sl":41,"el":55,"sc":3},{"sl":57,"el":69,"sc":3},{"sl":59,"el":61,"sc":7},{"sl":71,"el":77,"sc":3},{"sl":79,"el":82,"sc":3},{"sl":84,"el":90,"sc":3}],"el":91,"name":"LdapAttributeFilter"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_4":{"methods":[{"sl":41},{"sl":71},{"sl":79},{"sl":84}],"name":"testHashCodeAndEquals","statements":[{"sl":42},{"sl":44},{"sl":45},{"sl":50},{"sl":52},{"sl":73},{"sl":74},{"sl":76},{"sl":81},{"sl":86},{"sl":87},{"sl":89}],"pass":true},"test_10":{"methods":[{"sl":41},{"sl":57},{"sl":59}],"name":"testObjectClassConverter","statements":[{"sl":42},{"sl":44},{"sl":45},{"sl":50},{"sl":52},{"sl":58},{"sl":60},{"sl":64},{"sl":65}],"pass":true},"test_8":{"methods":[{"sl":41},{"sl":57},{"sl":59},{"sl":71}],"name":"testBogusFilterString","statements":[{"sl":42},{"sl":44},{"sl":45},{"sl":47},{"sl":50},{"sl":52},{"sl":53},{"sl":58},{"sl":60},{"sl":64},{"sl":65},{"sl":67},{"sl":73},{"sl":74},{"sl":76}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [10, 8, 4], [10, 8, 4], [], [10, 8, 4], [10, 8, 4], [], [8], [], [], [10, 8, 4], [], [10, 8, 4], [8], [], [], [], [10, 8], [10, 8], [10, 8], [10, 8], [], [], [], [10, 8], [10, 8], [], [8], [], [], [], [8, 4], [], [8, 4], [8, 4], [], [8, 4], [], [], [4], [], [4], [], [], [4], [], [4], [4], [], [4], [], []]

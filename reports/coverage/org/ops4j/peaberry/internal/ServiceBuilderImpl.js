var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":583,"sl":37,"methods":[{"sl":43,"el":45,"sc":3},{"sl":47,"el":49,"sc":3},{"sl":51,"el":54,"sc":3},{"sl":56,"el":59,"sc":3},{"sl":61,"el":64,"sc":3},{"sl":66,"el":69,"sc":3},{"sl":71,"el":74,"sc":3},{"sl":76,"el":79,"sc":3},{"sl":81,"el":84,"sc":3},{"sl":86,"el":89,"sc":3},{"sl":91,"el":94,"sc":3},{"sl":96,"el":99,"sc":3},{"sl":101,"el":103,"sc":3},{"sl":105,"el":107,"sc":3},{"sl":109,"el":111,"sc":3}],"el":112,"name":"ServiceBuilderImpl"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_29":{"methods":[{"sl":43},{"sl":66},{"sl":76},{"sl":101},{"sl":109}],"name":"configure","statements":[{"sl":44},{"sl":67},{"sl":68},{"sl":77},{"sl":78},{"sl":102},{"sl":110}],"pass":true},"test_11":{"methods":[{"sl":43},{"sl":101},{"sl":105},{"sl":109}],"name":"configure","statements":[{"sl":44},{"sl":102},{"sl":106},{"sl":110}],"pass":true},"test_9":{"methods":[{"sl":43},{"sl":96},{"sl":101},{"sl":105}],"name":"configure","statements":[{"sl":44},{"sl":97},{"sl":98},{"sl":102},{"sl":106}],"pass":true},"test_33":{"methods":[{"sl":43},{"sl":81},{"sl":101},{"sl":109}],"name":"configure","statements":[{"sl":44},{"sl":82},{"sl":83},{"sl":102},{"sl":110}],"pass":true},"test_20":{"methods":[{"sl":43},{"sl":56},{"sl":101},{"sl":109}],"name":"configure","statements":[{"sl":44},{"sl":57},{"sl":58},{"sl":102},{"sl":110}],"pass":true},"test_30":{"methods":[{"sl":43},{"sl":51},{"sl":101},{"sl":105}],"name":"configure","statements":[{"sl":44},{"sl":52},{"sl":53},{"sl":102},{"sl":106}],"pass":true},"test_7":{"methods":[{"sl":43},{"sl":101}],"name":"configure","statements":[{"sl":44},{"sl":102}],"pass":true},"test_31":{"methods":[{"sl":43},{"sl":101},{"sl":105},{"sl":109}],"name":"configure","statements":[{"sl":44},{"sl":102},{"sl":106},{"sl":110}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [], [], [], [], [30], [30], [30], [], [], [20], [20], [20], [], [], [], [], [], [], [], [29], [29], [29], [], [], [], [], [], [], [], [29], [29], [29], [], [], [33], [33], [33], [], [], [], [], [], [], [], [], [], [], [], [], [9], [9], [9], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [11, 31, 30, 9], [11, 31, 30, 9], [], [], [20, 11, 33, 31, 29], [20, 11, 33, 31, 29], [], []]
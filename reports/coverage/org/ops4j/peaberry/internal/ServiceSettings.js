var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":84,"sl":42,"methods":[{"sl":59,"el":70,"sc":3},{"sl":75,"el":79,"sc":3},{"sl":83,"el":85,"sc":3},{"sl":87,"el":89,"sc":3},{"sl":91,"el":93,"sc":3},{"sl":95,"el":97,"sc":3},{"sl":99,"el":101,"sc":3},{"sl":105,"el":114,"sc":3},{"sl":118,"el":121,"sc":3},{"sl":123,"el":125,"sc":3},{"sl":127,"el":142,"sc":3},{"sl":135,"el":137,"sc":11},{"sl":144,"el":155,"sc":3},{"sl":157,"el":163,"sc":3},{"sl":165,"el":178,"sc":3}],"el":179,"name":"ServiceSettings"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_29":{"methods":[{"sl":59},{"sl":87},{"sl":91},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":88},{"sl":92},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_11":{"methods":[{"sl":59},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_25":{"methods":[{"sl":157},{"sl":165}],"name":"testServiceExports","statements":[{"sl":158},{"sl":159},{"sl":160},{"sl":168},{"sl":169},{"sl":172},{"sl":173},{"sl":177}],"pass":true},"test_10":{"methods":[{"sl":118},{"sl":123},{"sl":127},{"sl":144},{"sl":157},{"sl":165}],"name":"testServiceLookupPerformance","statements":[{"sl":120},{"sl":124},{"sl":128},{"sl":129},{"sl":130},{"sl":131},{"sl":141},{"sl":145},{"sl":146},{"sl":149},{"sl":150},{"sl":154},{"sl":158},{"sl":159},{"sl":160},{"sl":168},{"sl":169},{"sl":172},{"sl":173},{"sl":177}],"pass":true},"test_9":{"methods":[{"sl":59},{"sl":95},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":96},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_32":{"methods":[{"sl":118},{"sl":123},{"sl":127},{"sl":144}],"name":"testDecoratedServiceInjection","statements":[{"sl":120},{"sl":124},{"sl":128},{"sl":129},{"sl":130},{"sl":131},{"sl":141},{"sl":145},{"sl":146},{"sl":149},{"sl":150},{"sl":154}],"pass":true},"test_33":{"methods":[{"sl":59},{"sl":99},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":100},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_15":{"methods":[{"sl":118},{"sl":123},{"sl":127},{"sl":144},{"sl":157},{"sl":165}],"name":"testDirectServiceInjection","statements":[{"sl":120},{"sl":124},{"sl":128},{"sl":129},{"sl":130},{"sl":131},{"sl":141},{"sl":145},{"sl":146},{"sl":149},{"sl":150},{"sl":154},{"sl":158},{"sl":159},{"sl":160},{"sl":168},{"sl":169},{"sl":172},{"sl":173},{"sl":177}],"pass":true},"test_20":{"methods":[{"sl":59},{"sl":83},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":84},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_30":{"methods":[{"sl":59},{"sl":83},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":84},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_7":{"methods":[{"sl":59},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":108},{"sl":110},{"sl":120}],"pass":true},"test_31":{"methods":[{"sl":59},{"sl":105},{"sl":118}],"name":"configure","statements":[{"sl":60},{"sl":63},{"sl":64},{"sl":69},{"sl":108},{"sl":110},{"sl":120}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [], [], [], [], [], [], [], [], [], [], [], [20, 30], [20, 30], [], [], [29], [29], [], [], [29], [29], [], [], [9], [9], [], [], [33], [33], [], [], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [20, 11, 7, 33, 31, 30, 29, 9], [], [20, 11, 7, 33, 31, 30, 29, 9], [], [], [], [], [], [], [], [20, 11, 7, 32, 15, 33, 31, 30, 10, 29, 9], [], [20, 11, 7, 32, 15, 33, 31, 30, 10, 29, 9], [], [], [32, 15, 10], [32, 15, 10], [], [], [32, 15, 10], [32, 15, 10], [32, 15, 10], [32, 15, 10], [32, 15, 10], [], [], [], [], [], [], [], [], [], [32, 15, 10], [], [], [32, 15, 10], [32, 15, 10], [32, 15, 10], [], [], [32, 15, 10], [32, 15, 10], [], [], [], [32, 15, 10], [], [], [15, 10, 25], [15, 10, 25], [15, 10, 25], [15, 10, 25], [], [], [], [], [15, 10, 25], [], [], [15, 10, 25], [15, 10, 25], [], [], [15, 10, 25], [15, 10, 25], [], [], [], [15, 10, 25], [], []]

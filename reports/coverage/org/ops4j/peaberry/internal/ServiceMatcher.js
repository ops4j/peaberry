var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 189, "sl" : 34, "el" : 93, "name" : "ServiceMatcher",
    "methods" : [
              {"sl" : 37, "el" : 37, "sc" : 3},  {"sl" : 45, "el" : 55, "sc" : 3},  {"sl" : 47, "el" : 49, "sc" : 7},  {"sl" : 51, "el" : 53, "sc" : 7},  {"sl" : 66, "el" : 92, "sc" : 3}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_22" : {
					  "name" : "leasedSpec",
					  "pass" : true ,
					  "methods" : [{"sl": 66 },],
					  "statements" : [{"sl": 69 },{"sl": 74 },{"sl": 76 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },]
					  },
		"test_4" : {
					  "name" : "serviceSpec",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },{"sl": 47 },{"sl": 66 },],
					  "statements" : [{"sl": 46 },{"sl": 48 },{"sl": 69 },{"sl": 74 },{"sl": 76 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 83 },{"sl": 84 },{"sl": 85 },{"sl": 86 },]
					  },
		"test_10" : {
					  "name" : "missingAnnotations",
					  "pass" : true ,
					  "methods" : [{"sl": 66 },],
					  "statements" : [{"sl": 69 },{"sl": 74 },{"sl": 76 },{"sl": 78 },{"sl": 79 },{"sl": 80 },{"sl": 81 },{"sl": 83 },{"sl": 85 },{"sl": 86 },{"sl": 91 },]
					  },
		"test_17" : {
					  "name" : "handlesNull",
					  "pass" : true ,
					  "methods" : [{"sl": 45 },{"sl": 47 },{"sl": 66 },],
					  "statements" : [{"sl": 46 },{"sl": 48 },{"sl": 69 },{"sl": 70 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 17 , 4   ] ,
  [ 17 , 4   ] ,
  [ 17 , 4   ] ,
  [ 17 , 4   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 22 , 10 , 17 , 4   ] ,
  [  ] ,
  [  ] ,
  [ 22 , 10 , 17 , 4   ] ,
  [ 17   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 22 , 10 , 4   ] ,
  [  ] ,
  [ 22 , 10 , 4   ] ,
  [  ] ,
  [ 22 , 10 , 4   ] ,
  [ 22 , 10 , 4   ] ,
  [ 22 , 10 , 4   ] ,
  [ 22 , 10 , 4   ] ,
  [  ] ,
  [ 22 , 10 , 4   ] ,
  [ 22 , 4   ] ,
  [ 22 , 10 , 4   ] ,
  [ 22 , 10 , 4   ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 10   ] ,
  [  ] ,
  [  ] 
];

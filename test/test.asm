  0         PUSH         1
  1         PUSH         1
  2         LOADL        0
  3         CALL         newarr  
  4         CALL         L15
  5         HALT   (0)   
  6  L10:   LOAD         -1[LB]
  7         CALL         putintnl
  8         RETURN (0)   1
  9         CALL         puteol  
 10         RETURN (0)   0
 11         LOAD         -1[LB]
 12         JUMPIF (0)   L11
 13         LOADL        116
 14         CALL         put     
 15         LOADL        114
 16         CALL         put     
 17         LOADL        117
 18         CALL         put     
 19         LOADL        101
 20         CALL         put     
 21         CALL         puteol  
 22         RETURN (0)   1
 23  L11:   LOADL        102
 24         CALL         put     
 25         LOADL        97
 26         CALL         put     
 27         LOADL        108
 28         CALL         put     
 29         LOADL        115
 30         CALL         put     
 31         LOADL        101
 32         CALL         put     
 33         CALL         puteol  
 34         RETURN (0)   1
 35         PUSH         1
 36         LOADL        0
 37         LOAD         -1[LB]
 38         CALL         arraylen
 39         STORE        3[LB]
 40  L12:   LOAD         3[LB]
 41         LOAD         4[LB]
 42         CALL         sub     
 43         JUMPIF (0)   L13
 44         LOAD         -1[LB]
 45         LOAD         4[LB]
 46         CALL         arrayref
 47         CALL         put     
 48         CALL         succ    
 49         JUMP         L12
 50  L13:   CALL         puteol  
 51         RETURN (0)   1
 52  L14:   LOAD         0[OB]
 53         CALL         arraylen
 54         RETURN (1)   0
 55  L15:   PUSH         1
 56         LOADL        1
 57         STORE        3[LB]
 58         LOADL        5
 59         CALL         newarr  
 60         LOAD         -1[ST]
 61         LOADL        0
 62         LOADL        104
 63         CALL         arrayupd
 64         LOAD         -1[ST]
 65         LOADL        1
 66         LOADL        101
 67         CALL         arrayupd
 68         LOAD         -1[ST]
 69         LOADL        2
 70         LOADL        108
 71         CALL         arrayupd
 72         LOAD         -1[ST]
 73         LOADL        3
 74         LOADL        108
 75         CALL         arrayupd
 76         LOAD         -1[ST]
 77         LOADL        4
 78         LOADL        111
 79         CALL         arrayupd
 80         STORE        1[SB]
 81         LOAD         1[SB]
 82         CALLI        L14
 83         CALL         L10
 84         RETURN (0)   1

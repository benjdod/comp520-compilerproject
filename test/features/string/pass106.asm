  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L18
  4         HALT   (0)   
  5         LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L10
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L10:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33  L11:   LOAD         -1[LB]
 34         JUMPIF (0)   L13
 35         PUSH         1
 36         LOADL        0
 37         LOAD         -1[LB]
 38         CALL         arraylen
 39         STORE        3[LB]
 40  L12:   LOAD         3[LB]
 41         LOAD         4[LB]
 42         CALL         sub     
 43         JUMPIF (0)   L14
 44         LOAD         -1[LB]
 45         LOAD         4[LB]
 46         CALL         arrayref
 47         CALL         put     
 48         CALL         succ    
 49         JUMP         L12
 50  L13:   LOADL        110
 51         CALL         put     
 52         LOADL        117
 53         CALL         put     
 54         LOADL        108
 55         CALL         put     
 56         LOADL        108
 57         CALL         put     
 58  L14:   CALL         puteol  
 59         RETURN (0)   1
 60  L15:   LOAD         -2[LB]
 61         LOAD         -1[LB]
 62         CALL         eq      
 63         JUMPIF (0)   L16
 64         LOADL        17
 65         CALL         newarr  
 66         LOAD         -1[ST]
 67         LOADL        0
 68         LOADL        115
 69         CALL         arrayupd
 70         LOAD         -1[ST]
 71         LOADL        1
 72         LOADL        116
 73         CALL         arrayupd
 74         LOAD         -1[ST]
 75         LOADL        2
 76         LOADL        114
 77         CALL         arrayupd
 78         LOAD         -1[ST]
 79         LOADL        3
 80         LOADL        105
 81         CALL         arrayupd
 82         LOAD         -1[ST]
 83         LOADL        4
 84         LOADL        110
 85         CALL         arrayupd
 86         LOAD         -1[ST]
 87         LOADL        5
 88         LOADL        103
 89         CALL         arrayupd
 90         LOAD         -1[ST]
 91         LOADL        6
 92         LOADL        115
 93         CALL         arrayupd
 94         LOAD         -1[ST]
 95         LOADL        7
 96         LOADL        32
 97         CALL         arrayupd
 98         LOAD         -1[ST]
 99         LOADL        8
100         LOADL        97
101         CALL         arrayupd
102         LOAD         -1[ST]
103         LOADL        9
104         LOADL        114
105         CALL         arrayupd
106         LOAD         -1[ST]
107         LOADL        10
108         LOADL        101
109         CALL         arrayupd
110         LOAD         -1[ST]
111         LOADL        11
112         LOADL        32
113         CALL         arrayupd
114         LOAD         -1[ST]
115         LOADL        12
116         LOADL        101
117         CALL         arrayupd
118         LOAD         -1[ST]
119         LOADL        13
120         LOADL        113
121         CALL         arrayupd
122         LOAD         -1[ST]
123         LOADL        14
124         LOADL        117
125         CALL         arrayupd
126         LOAD         -1[ST]
127         LOADL        15
128         LOADL        97
129         CALL         arrayupd
130         LOAD         -1[ST]
131         LOADL        16
132         LOADL        108
133         CALL         arrayupd
134         JUMP         L17
135  L16:   LOADL        21
136         CALL         newarr  
137         LOAD         -1[ST]
138         LOADL        0
139         LOADL        115
140         CALL         arrayupd
141         LOAD         -1[ST]
142         LOADL        1
143         LOADL        116
144         CALL         arrayupd
145         LOAD         -1[ST]
146         LOADL        2
147         LOADL        114
148         CALL         arrayupd
149         LOAD         -1[ST]
150         LOADL        3
151         LOADL        105
152         CALL         arrayupd
153         LOAD         -1[ST]
154         LOADL        4
155         LOADL        110
156         CALL         arrayupd
157         LOAD         -1[ST]
158         LOADL        5
159         LOADL        103
160         CALL         arrayupd
161         LOAD         -1[ST]
162         LOADL        6
163         LOADL        115
164         CALL         arrayupd
165         LOAD         -1[ST]
166         LOADL        7
167         LOADL        32
168         CALL         arrayupd
169         LOAD         -1[ST]
170         LOADL        8
171         LOADL        97
172         CALL         arrayupd
173         LOAD         -1[ST]
174         LOADL        9
175         LOADL        114
176         CALL         arrayupd
177         LOAD         -1[ST]
178         LOADL        10
179         LOADL        101
180         CALL         arrayupd
181         LOAD         -1[ST]
182         LOADL        11
183         LOADL        32
184         CALL         arrayupd
185         LOAD         -1[ST]
186         LOADL        12
187         LOADL        110
188         CALL         arrayupd
189         LOAD         -1[ST]
190         LOADL        13
191         LOADL        111
192         CALL         arrayupd
193         LOAD         -1[ST]
194         LOADL        14
195         LOADL        116
196         CALL         arrayupd
197         LOAD         -1[ST]
198         LOADL        15
199         LOADL        32
200         CALL         arrayupd
201         LOAD         -1[ST]
202         LOADL        16
203         LOADL        101
204         CALL         arrayupd
205         LOAD         -1[ST]
206         LOADL        17
207         LOADL        113
208         CALL         arrayupd
209         LOAD         -1[ST]
210         LOADL        18
211         LOADL        117
212         CALL         arrayupd
213         LOAD         -1[ST]
214         LOADL        19
215         LOADL        97
216         CALL         arrayupd
217         LOAD         -1[ST]
218         LOADL        20
219         LOADL        108
220         CALL         arrayupd
221  L17:   CALL         L11
222         RETURN (0)   2
223  L18:   PUSH         1
224         LOADL        3
225         CALL         newarr  
226         LOAD         -1[ST]
227         LOADL        0
228         LOADL        46
229         CALL         arrayupd
230         LOAD         -1[ST]
231         LOADL        1
232         LOADL        46
233         CALL         arrayupd
234         LOAD         -1[ST]
235         LOADL        2
236         LOADL        46
237         CALL         arrayupd
238         STORE        3[LB]
239         PUSH         1
240         LOADL        0
241         STORE        4[LB]
242         LOAD         3[LB]
243         LOAD         4[LB]
244         CALL         L15
245         LOAD         3[LB]
246         STORE        4[LB]
247         LOAD         3[LB]
248         LOAD         4[LB]
249         CALL         L15
250         RETURN (0)   1
import java.io.*;
import java.util.*;

// ╔══════════════════════════════════════════════════════════════╗
// ║         CYBERSHIELD — DSA Java (All 11 Bugs Fixed)           ║
// ║  CO1: Sorting & Searching | CO2: Arrays & Linked Lists       ║
// ║  CO3: Stack, Queue, Heap  | CO4: HashMap & Collections       ║
// ║  CO5: Practical App       | CO6: Real-World Program          ║
// ╚══════════════════════════════════════════════════════════════╝
public class CyberShield {

    // ── CO2: User ADT ─────────────────────────────────────────
    static class User {
        String name, email, pass;
        User(String n, String e, String p){ name=n; email=e; pass=p; }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
    }

    // ── CO2: Singly Linked List Node (Awareness Modules) ──────
    static class ModuleNode {
        String icon, title; String[] dos, avoids; ModuleNode next;
        ModuleNode(String i,String t,String[] d,String[] a){
            icon=i; title=t; dos=d; avoids=a;
        }
    }
    static ModuleNode head = null;

    // CO2 — SLL Insert at tail O(n)
    static void llInsert(String i,String t,String[] d,String[] a){
        ModuleNode n=new ModuleNode(i,t,d,a);
        if(head==null){head=n;return;}
        ModuleNode c=head; while(c.next!=null)c=c.next; c.next=n;
    }
    // CO2 — SLL Traverse O(n)
    static void llTraverse(){
        ModuleNode c=head; int i=1;
        while(c!=null){
            System.out.println("  ["+i+"] "+c.icon+" "+c.title);
            c=c.next; i++;
        }
    }
    // CO2 — SLL Get by position O(n)
    static ModuleNode llGet(int pos){
        ModuleNode c=head; int i=1;
        while(c!=null){ if(i==pos)return c; c=c.next; i++; }
        return null;
    }
    // CO2 — SLL Reverse O(n)
    static void llReverse(){
        ModuleNode prev=null,c=head,nxt;
        while(c!=null){ nxt=c.next; c.next=prev; prev=c; c=nxt; }
        head=prev;
    }

    // ── CO3: NavStack (FIX BUG 7+8: renamed to avoid java.util.Stack conflict) ─
    static class NavStack {
        String[] d=new String[50]; int top=-1;
        void push(String s){ if(top<49)d[++top]=s; }    // O(1)
        @SuppressWarnings("unused")
        String pop()       { return top>=0?d[top--]:null; } // O(1)
        @SuppressWarnings("unused")
        boolean isEmpty()  { return top==-1; }
    }

    // ── CO3: Circular Queue (Live Threat Feed) ─────────────────
    static class CQueue {
        String[] d; int front=0,rear=-1,size=0,cap;
        CQueue(int c){ cap=c; d=new String[c]; }
        // CO3 — enqueue O(1) — mirrors JS: fi=(fi+1)%FEV.length
        void enqueue(String s){
            if(size==cap){ front=(front+1)%cap; size--; } // evict oldest
            rear=(rear+1)%cap; d[rear]=s; size++;
        }
        void print(){
            if(size==0){ System.out.println("  (feed empty)"); return; }
            System.out.println("  Live Threat Feed (Circular Queue, cap=5):");
            for(int i=0;i<size;i++)
                System.out.println("  🔴 "+d[(front+i)%cap]);
        }
    }

    // ── CO3: Max Heap (Leaderboard) ───────────────────────────
    static class MaxHeap {
        int[] s=new int[50]; String[] n=new String[50]; int sz=0;
        // CO3 — insert + heapify up O(log n)
        void insert(String name,int score){
            s[sz]=score; n[sz]=name; int i=sz++;
            while(i>0 && s[i]>s[(i-1)/2]){
                int p=(i-1)/2;
                int ts=s[i]; s[i]=s[p]; s[p]=ts;
                String tn=n[i]; n[i]=n[p]; n[p]=tn;
                i=p;
            }
        }
        void print(){
            System.out.println("  Leaderboard (MaxHeap — O(log n) insert):");
            for(int i=0;i<sz;i++)
                System.out.printf("  #%d %-18s %d pts%n",i+1,n[i],s[i]);
        }
    }

    // ── CO4: HashMap — User Store + Router ────────────────────
    static HashMap<String,User>     users  = new HashMap<>(); // O(1) lookup
    static HashMap<String,Runnable> router = new HashMap<>(); // O(1) dispatch

    // ── CO1: Bubble Sort — sort threats by name ────────────────
    static String[] bubbleSort(String[] arr){
        String[] a=arr.clone();
        for(int i=0;i<a.length-1;i++)               // O(n²)
            for(int j=0;j<a.length-i-1;j++)
                if(a[j].compareTo(a[j+1])>0){
                    String t=a[j]; a[j]=a[j+1]; a[j+1]=t;
                }
        return a;
    }

    // ── CO1: Binary Search — find category in sorted array ────
    static int binarySearch(String[] arr,String key){
        int lo=0,hi=arr.length-1;
        while(lo<=hi){                               // O(log n)
            int mid=(lo+hi)/2;
            int c=arr[mid].compareToIgnoreCase(key);
            if(c==0) return mid;
            else if(c<0) lo=mid+1;
            else hi=mid-1;
        }
        return -1;
    }

    // ── CO1: Linear Search — find threat by name ──────────────
    static int linearSearch(String[] arr,String key){
        for(int i=0;i<arr.length;i++)               // O(n)
            if(arr[i].equalsIgnoreCase(key)) return i;
        return -1;
    }

    // ── GLOBAL STATE ───────────────────────────────────────────
    static Scanner  sc   = new Scanner(System.in);
    static User     me   = null;
    static NavStack nav  = new NavStack();   // FIX BUG 7+8: renamed class
    static CQueue   feed = new CQueue(5);
    static MaxHeap  heap = new MaxHeap();

    // TH[] — 6 Threat Profiles (maps to JS TH[])
    static String[] threats  = {
        "Phishing Attacks","Ransomware","Social Engineering",
        "Password Attacks","Man-in-the-Middle","SQL Injection"
    };
    static String[] severity = {
        "CRITICAL","CRITICAL","HIGH","HIGH","HIGH","HIGH"
    };
    static String[][] signs = {
        {"Urgent email language","Sender domain mismatch"},
        {"Files renamed .encrypted","Ransom note on desktop"},
        {"Unsolicited authority contact","Pressure tactics"},
        {"Unexpected login alerts","Account lockouts"},
        {"Unexpected cert warnings","Slow internet on WiFi"},
        {"DB errors on website","Login bypass"}
    };
    static String[][] defenses = {
        {"Verify sender email before clicking","Enable MFA on all accounts"},
        {"Maintain offline backups (3-2-1 rule)","Never pay ransom"},
        {"Legitimate IT never needs your password","Verify via official channels"},
        {"Use a password manager","Enable MFA everywhere"},
        {"Use VPN on public WiFi","Always verify HTTPS"},
        {"Use parameterized queries","Implement Web Application Firewall"}
    };

    // QS[] — 10 Quiz Questions (maps to JS QS[])
    static String[] qCat = {
        "PHISHING","PASSWORDS","RANSOMWARE","SOCIAL ENG.","PUBLIC WI-FI",
        "UPDATES","MFA","BREACHES","MALWARE","NETWORK"
    };
    static String[] qText = {
        "Email from security@paypa1.com asks to verify. What do you do?",
        "Which password strategy is MOST secure?",
        "Files renamed .encrypted, Bitcoin demand appears. FIRST action?",
        "IT calls needing your password. They know your name. What do you do?",
        "Need to check bank at coffee shop. Free WiFi available. Safest?",
        "Windows shows critical security updates. You're mid-work. Do?",
        "Site offers SMS or Authenticator App for 2FA. Which?",
        "Email says data breached with link to check. What do you do?",
        "Downloaded free software. Antivirus warns before running. Do?",
        "Setting up home WiFi router. Most important action?"
    };
    static String[][] qOpts = {
        {"A. Click and verify immediately",
         "B. Check domain — paypa1 uses 1 not l",
         "C. Forward to contacts to warn them",
         "D. Reply asking if it is legitimate"},
        {"A. One strong password used everywhere",
         "B. Password manager with unique passwords per account",
         "C. Write unique passwords on paper in a drawer",
         "D. Email plus birthdate combo"},
        {"A. Pay the ransom immediately",
         "B. Disconnect from network then restore from backup",
         "C. Manually delete the ransomware files",
         "D. Restart the computer"},
        {"A. Give it — they know your details",
         "B. Decline — legitimate IT never needs your password",
         "C. Give a temporary password you will change later",
         "D. Ask them to email you officially first"},
        {"A. Connect — HTTPS makes it safe",
         "B. Connect but avoid the bank site",
         "C. Use mobile data or personal hotspot with VPN",
         "D. Ask barista for the official network name"},
        {"A. Disable automatic updates to avoid interruption",
         "B. Schedule the update for today once work is saved",
         "C. Ignore — updates are mostly feature additions",
         "D. Wait a month for the update to stabilize"},
        {"A. SMS — simpler and faster in daily use",
         "B. Authenticator app — SMS can be intercepted via SIM swapping",
         "C. Either — the difference is negligible",
         "D. Neither — 2FA makes recovery harder"},
        {"A. Click — sent by the company to help you",
         "B. Ignore — probably spam",
         "C. Go directly to HaveIBeenPwned.com without clicking the link",
         "D. Forward to friends to warn them"},
        {"A. Allow anyway — antivirus gives false positives",
         "B. Delete immediately — pirated software is the #1 malware vector",
         "C. Disable antivirus temporarily to install",
         "D. Run it but disconnect internet first"},
        {"A. Use WEP — most widely compatible",
         "B. Change default admin credentials and enable WPA3",
         "C. Leave defaults — manufacturers configure routers securely",
         "D. Hide SSID — invisible means unattackable"}
    };
    static int[]    qAns = {1,1,1,1,2,1,1,2,1,1};
    static String[] qExp = {
        "paypa1.com uses the number 1 instead of letter l — classic typosquatting!",
        "Password manager with unique passwords eliminates credential stuffing risk.",
        "Disconnect first to stop spread to other devices. Never pay ransom.",
        "Legitimate IT departments NEVER need your password. This is pretexting.",
        "Public WiFi is untrustworthy. Even HTTPS cannot protect against all MitM attacks.",
        "Critical patches fix actively-exploited zero-days. Schedule immediately.",
        "Authenticator apps generate codes locally. SMS is vulnerable to SIM swapping.",
        "Never click links in breach emails. Go directly to haveibeenpwned.com.",
        "Pirated software is the number 1 malware delivery method. Never override the warning.",
        "Default credentials are publicly known. WEP is completely broken."
    };

    // ══════════════════════════════════════════════════════════
    // MAIN — opens website then runs DSA console
    // ══════════════════════════════════════════════════════════
    public static void main(String[] args) {
        System.out.println(banner());
        openWebsite();   // ← opens cybershield.html in browser
        loadData();
        setupRouter();
        loginScreen();
    }

    // ── FIX BUG 1,2,11: Open website — all OS + space-safe paths ─
    static void openWebsite(){
        // Search for the HTML file in current directory
        File f = null;
        String[] names = {"cybershield.html","CyberShield.html","index.html"};
        for(String name : names){
            File tmp = new File(System.getProperty("user.dir"), name);
            if(tmp.exists()){ f=tmp; break; }
        }

        if(f != null && f.exists()){
            try{
                String path = f.getAbsolutePath();
                String os   = System.getProperty("os.name").toLowerCase();

                // FIX BUG 1: Windows — use array form to handle spaces in path
                if(os.contains("win")){
                    Runtime.getRuntime().exec(
                        new String[]{"rundll32","url.dll,FileProtocolHandler",path});
                }
                // FIX BUG 2: Mac — use array form to handle spaces in path
                else if(os.contains("mac")){
                    Runtime.getRuntime().exec(new String[]{"open",path});
                }
                // Linux
                else{
                    Runtime.getRuntime().exec(new String[]{"xdg-open",path});
                }
                System.out.println("  Website opened in browser!");
                System.out.println("  File: "+path+"\n");
            }catch(IOException e){
                System.out.println("  Could not auto-open browser: "+e.getMessage());
                System.out.println("  Manually open cybershield.html in your browser.\n");
            }
        } else {
            // FIX BUG 11: clear message, no duplicate file check
            System.out.println("  ╔══════════════════════════════════════════════╗");
            System.out.println("  ║  STEP: Put cybershield.html in same folder   ║");
            System.out.println("  ║  as CyberShield.java, then run again.        ║");
            System.out.println("  ║  Current folder: "+
                System.getProperty("user.dir").substring(
                Math.max(0,System.getProperty("user.dir").length()-30))+"  ║");
            System.out.println("  ╚══════════════════════════════════════════════╝\n");
        }
    }

    // ── CO4: Setup HashMap router ──────────────────────────────
    static void setupRouter(){
        router.put("home",      ()-> showHome());
        router.put("threats",   ()-> showThreats());
        router.put("awareness", ()-> showAwareness());
        router.put("quiz",      ()-> runQuiz());
        router.put("sort",      ()-> showSort());
        router.put("logout",    ()-> logout());
    }

    // CO4 — navigate: HashMap O(1) + Stack push O(1)
    static void go(String page){
        nav.push(page);                   // CO3 — Stack push O(1)
        Runnable r = router.get(page);    // CO4 — HashMap.get() O(1)
        if(r != null) r.run();
    }

    // ── LOAD DATA ──────────────────────────────────────────────
    static void loadData(){
        // CO4 — HashMap: pre-load demo users
        users.put("demo@test.com",  new User("Demo User","demo@test.com","demo123"));
        users.put("admin@cs.com",   new User("Admin","admin@cs.com","admin123"));

        // CO3 — Circular Queue: live feed (maps to JS FEV[])
        feed.enqueue("Ransomware attack — Healthcare sector, US-East");
        feed.enqueue("Phishing campaign — 2,400 emails blocked");
        feed.enqueue("SQL injection on govt portal — mitigated");
        feed.enqueue("DDoS 380 Gbps peak — CDN provider");
        feed.enqueue("Critical patch — CVE-2025-0312 resolved");

        // CO2 — SLL: awareness modules (maps to JS AM[])
        llInsert("🔑","Password Security",
            new String[]{"Use 16+ char passphrases","Use password manager","Enable MFA"},
            new String[]{"Reusing passwords","Using personal info","Patterns like Password1!"});
        llInsert("📧","Email Safety",
            new String[]{"Check full sender email","Hover links to preview URL"},
            new String[]{"Clicking unexpected links","Opening unknown attachments"});
        llInsert("🌐","Safe Browsing",
            new String[]{"Verify HTTPS before entering data","Use VPN on public WiFi"},
            new String[]{"Ignoring security warnings","Downloading cracked software"});
        llInsert("📱","Mobile Security",
            new String[]{"Enable screen lock","Install apps from official stores only"},
            new String[]{"Jailbreaking device","Public USB charging (juice jacking)"});
        llInsert("☁️","Cloud & Data Safety",
            new String[]{"Review file access permissions","Enable login notifications"},
            new String[]{"Sharing via public links","Ignoring security alerts"});
    }

    // ══════════════════════════════════════════════════════════
    // LOGIN / REGISTER
    // ══════════════════════════════════════════════════════════
    static void loginScreen(){
        while(true){
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║   🛡  CYBERSHIELD CONSOLE     ║");
            System.out.println("║  [1] Login  [2] Register      ║");
            System.out.println("║  [0] Exit                     ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.print("  Choice: ");
            switch(sc.nextLine().trim()){
                case "1" -> doLogin();
                case "2" -> doRegister();
                case "0" -> { System.out.println("  Goodbye! Stay Secure. 🛡"); System.exit(0); }
                default  -> System.out.println("  Invalid choice. Try again.");
            }
        }
    }

    // FIX BUG 6: no auto-create on login — must register first
    static void doLogin(){
        System.out.print("  Email   : "); String email = sc.nextLine().trim();
        System.out.print("  Password: "); String pw    = sc.nextLine().trim();
        if(!email.contains("@")){
            System.out.println("  Invalid email address."); return;
        }
        User u = users.get(email);   // CO4 — HashMap.get() O(1)
        if(u == null){
            System.out.println("  Email not found. Please register first."); return;
        }
        if(!u.pass.equals(pw)){
            System.out.println("  Wrong password. Try again."); return;
        }
        me = u;
        System.out.println("\n  Welcome, "+me.name+"!\n");
        mainMenu();
    }

    static void doRegister(){
        System.out.print("  Full Name: "); String name  = sc.nextLine().trim();
        System.out.print("  Email    : "); String email = sc.nextLine().trim();
        System.out.print("  Password : "); String pw    = sc.nextLine().trim();
        if(name.isEmpty()){
            System.out.println("  Name cannot be empty."); return; }
        if(!email.contains("@")){
            System.out.println("  Invalid email address."); return; }
        if(pw.length()<6){
            System.out.println("  Password must be at least 6 characters."); return; }
        if(users.containsKey(email)){   // CO4 — O(1)
            System.out.println("  Email already registered. Please login."); return; }
        users.put(email, new User(name,email,pw));
        System.out.println("  Account created! You can now login.");
    }

    // ══════════════════════════════════════════════════════════
    // MAIN MENU
    // ══════════════════════════════════════════════════════════
    // FIX BUG 9: removed emoji from printf format — plain text for alignment
    static void mainMenu(){
        while(me != null){
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║   CYBERSHIELD — "+me.name.substring(0,Math.min(me.name.length(),12)));
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ [1] Home     [2] Threats      ║");
            System.out.println("║ [3] Awareness [4] Quiz        ║");
            System.out.println("║ [5] Sort Demo [0] Logout      ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.print("  Choice: ");
            switch(sc.nextLine().trim()){
                case "1" -> go("home");
                case "2" -> go("threats");
                case "3" -> go("awareness");
                case "4" -> go("quiz");
                case "5" -> go("sort");
                case "0" -> go("logout");
                default  -> System.out.println("  Invalid. Enter 0-5.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    // CO5 — HOME (maps to JS rHome())
    // ══════════════════════════════════════════════════════════
    static void showHome(){
        System.out.println("\n── HOME DASHBOARD ──────────────────────");
        System.out.println("  4.2B Records Breached 2024 | $4.8M Avg Breach Cost");
        System.out.println("  94% Attacks via Email | 277 Days Avg Detection\n");
        feed.print();   // CO3 — Circular Queue
        System.out.println("\n  THREAT CATEGORIES (TH[] array O(n) traversal):");
        for(int i=0;i<threats.length;i++)   // CO2 — array O(1) index
            System.out.printf("  %-22s [%s]%n", threats[i], severity[i]);
        enter();
    }

    // ══════════════════════════════════════════════════════════
    // CO5 — THREATS (maps to JS rThreats() + togTh())
    // FIX BUG 3: bounds check added
    // FIX BUG 4: while loop instead of recursion
    // ══════════════════════════════════════════════════════════
    static void showThreats(){
        while(true){   // FIX BUG 4: loop instead of recursion
            System.out.println("\n── THREATS LIBRARY ─────────────────────");
            for(int i=0;i<threats.length;i++)
                System.out.printf("  [%d] %-22s [%s]%n",i+1,threats[i],severity[i]);
            System.out.print("\n  Expand (1-6), 0=back: ");
            String in = sc.nextLine().trim();
            if(in.equals("0")) return;
            try{
                int idx = Integer.parseInt(in) - 1;
                // FIX BUG 3: bounds check before array access
                if(idx < 0 || idx >= threats.length){
                    System.out.println("  Enter a number between 1 and 6."); continue;
                }
                System.out.println("\n  -- "+threats[idx]+" ["+severity[idx]+"] --");
                System.out.println("  WARNING SIGNS:");
                for(String s : signs[idx])    System.out.println("    > "+s);
                System.out.println("  DEFENSES:");
                for(String d : defenses[idx]) System.out.println("    + "+d);
                enter();
            }catch(NumberFormatException e){
                System.out.println("  Enter a number between 1 and 6.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    // CO5 — AWARENESS (maps to JS rAwareness() + togAcc())
    // CO2 — SLL: traverse, search, reverse
    // FIX BUG 5: while loop instead of recursion
    // ══════════════════════════════════════════════════════════
    static void showAwareness(){
        while(true){   // FIX BUG 5: loop instead of recursion
            System.out.println("\n── SECURITY AWARENESS ──────────────────");
            System.out.println("  [Singly Linked List — O(n) traversal]");
            llTraverse();
            System.out.print("\n  Open (1-5), R=Reverse, 0=back: ");
            String in = sc.nextLine().trim().toUpperCase();
            if(in.equals("0")) return;
            if(in.equals("R")){
                llReverse();   // CO2 — SLL reverse O(n)
                System.out.println("  List reversed!");
                continue;
            }
            try{
                int pos = Integer.parseInt(in);
                ModuleNode n = llGet(pos);   // CO2 — SLL search O(n)
                if(n == null){
                    System.out.println("  Enter a number between 1 and 5."); continue;
                }
                System.out.println("\n  "+n.icon+" "+n.title);
                System.out.println("  DO:");
                for(String d : n.dos)    System.out.println("    + "+d);
                System.out.println("  AVOID:");
                for(String a : n.avoids) System.out.println("    - "+a);
                enter();
            }catch(NumberFormatException e){
                System.out.println("  Enter a number between 1 and 5 or R.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════
    // CO5 — QUIZ (maps to JS startQ() + selAns() + renderRes())
    // CO1: Recurrence T(n)=T(n-1)-1 (timer countdown model)
    // CO3: MaxHeap insert O(log n)
    // FIX BUG 10: streak bonus — check BEFORE incrementing streak
    // ══════════════════════════════════════════════════════════
    static void runQuiz(){
        System.out.println("\n── QUIZ ────────────────────────────────");
        System.out.println("  10 Questions | 10 pts each | Streak Bonus +5");
        System.out.print("  Press ENTER to start..."); sc.nextLine();
        int score=0, streak=0;
        for(int i=0;i<qText.length;i++){   // CO2 — array traversal
            System.out.println("\n  Q"+(i+1)+"/10  ["+qCat[i]+"]  Score: "+score+"  Streak: "+streak);
            System.out.println("  "+qText[i]+"\n");
            for(String o : qOpts[i]) System.out.println("  "+o);  // CO2 — array traversal
            System.out.print("\n  Answer (A/B/C/D): ");
            String ans = sc.nextLine().trim().toUpperCase();
            int ch = (!ans.isEmpty() && ans.charAt(0)>='A' && ans.charAt(0)<='D')
                     ? ans.charAt(0)-'A' : -1;
            if(ch == qAns[i]){
                // FIX BUG 10: check streak BEFORE incrementing
                int bonus = (streak >= 3) ? 5 : 0;
                score += 10 + bonus;
                streak++;
                System.out.println("  CORRECT! +"+(10+bonus)+(bonus>0?"  STREAK BONUS!":""));
            }else{
                streak = 0;
                System.out.println("  WRONG! Correct answer: "+qOpts[i][qAns[i]]);
            }
            System.out.println("  Tip: "+qExp[i]);
            // CO1: T(n)=T(n-1)-1 — timer recurrence: each question step reduces time by 1
        }
        // CO1 — linear search on grade thresholds O(n)
        String grade = score>=90 ? "EXPERT" :
                       score>=70 ? "PROFICIENT" :
                       score>=50 ? "DEVELOPING" : "VULNERABLE";
        System.out.println("\n  ╔══════════════════════════╗");
        System.out.printf( "  ║ Score: %3d/100  %-10s║%n", score, grade);
        System.out.println("  ╚══════════════════════════╝");
        heap.insert(me.name, score);   // CO3 — MaxHeap O(log n)
        heap.print();
        enter();
    }

    // ══════════════════════════════════════════════════════════
    // CO1 — SORT DEMO: all algorithms + complexity table
    // ══════════════════════════════════════════════════════════
    static void showSort(){
        System.out.println("\n── SORTING DEMO (CO1) ──────────────────");

        // CO1 — Bubble Sort O(n²) on threats[]
        String[] sorted = bubbleSort(threats);
        System.out.print("  Bubble Sort O(n^2): ");
        for(String t : sorted) System.out.print(t.split(" ")[0]+" ");
        System.out.println();

        // CO1 — Binary Search O(log n)
        String[] catsSorted = qCat.clone();
        Arrays.sort(catsSorted);
        int bi = binarySearch(catsSorted,"PHISHING");
        System.out.println("  Binary Search O(log n)  PHISHING: index="+bi);

        // CO1 — Linear Search O(n)
        int li = linearSearch(threats,"Ransomware");
        System.out.println("  Linear Search  O(n)     Ransomware: index="+li);

        System.out.println("\n  COMPLEXITY TABLE:");
        System.out.println("  +------------------+----------+----------+");
        System.out.println("  | Algorithm        | Average  | Worst    |");
        System.out.println("  +------------------+----------+----------+");
        System.out.println("  | Bubble Sort      | O(n^2)   | O(n^2)   |");
        System.out.println("  | Selection Sort   | O(n^2)   | O(n^2)   |");
        System.out.println("  | Insertion Sort   | O(n^2)   | O(n^2)   |");
        System.out.println("  | Merge Sort       | O(nlogn) | O(nlogn) |");
        System.out.println("  | Quick Sort       | O(nlogn) | O(n^2)   |");
        System.out.println("  | Linear Search    | O(n)     | O(n)     |");
        System.out.println("  | Binary Search    | O(logn)  | O(logn)  |");
        System.out.println("  | HashMap get/put  | O(1)     | O(1)     |");
        System.out.println("  | Stack push/pop   | O(1)     | O(1)     |");
        System.out.println("  | Heap insert      | O(logn)  | O(logn)  |");
        System.out.println("  +------------------+----------+----------+");

        System.out.println("\n  RECURRENCE RELATIONS:");
        System.out.println("  Merge Sort:    T(n) = 2T(n/2) + O(n) -> O(n log n)");
        System.out.println("  Quiz Timer:    T(n) = T(n-1)  - 1    -> O(n)");
        System.out.println("  Binary Search: T(n) = T(n/2)  + O(1) -> O(log n)");
        enter();
    }

    static void logout(){
        System.out.println("  Goodbye, "+me.name+"! Stay Secure.\n");
        me = null;
    }

    static void enter(){
        System.out.print("\n  [Press ENTER to continue] ");
        sc.nextLine();
    }

    static String banner(){
        return
        """
        \u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557
        \u2551   CYBERSHIELD \u2014 DSA Java             \u2551
        \u2551   CO1: Sorting + Searching           \u2551
        \u2551   CO2: Arrays + Linked Lists         \u2551
        \u2551   CO3: Stack + Queue + Heap          \u2551
        \u2551   CO4: HashMap + Collections         \u2551
        \u2551   Opening website in browser...      \u2551
        \u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d
        """;
    }
}
public class Body {
    // Instance variables for Body as well as the G constant.
	public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double g = 6.67 * Math.pow(10,-11);

    // Body constructor
    public Body (double xxP, double yyP, double xxV, double yyV, double m, String img){
        xxPos = xxP;
        yyPos = yyP;
        xxVel = xxV;
        yyVel = yyV;
        mass = m;
        imgFileName = img;
    }

    // Duplicate body constructor
    public Body (Body b){
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }   

    // Difference between xPos and yPos between bodies, used for convenience.
    private double xchange (Body b){
        return this.xxPos - b.xxPos;
    }
    private double ychange (Body b){
        return this.yyPos - b.yyPos;
    }
    
    // Finds distance between two bodies
    public double calcDistance (Body b){
        return Math.sqrt((xchange(b) * xchange(b)) + (ychange(b) * ychange(b)));
    }

    // Force calculation methods
    public double calcForceExertedBy (Body b){
        return (g * this.mass * b.mass) / (calcDistance(b) * calcDistance(b));
    }
    public double calcForceExertedByX (Body b) {
        double f = (calcForceExertedBy(b) * xchange(b)) / calcDistance(b);
        return -f;
    }
    public double calcForceExertedByY (Body b){
        double f = (calcForceExertedBy(b) * ychange(b)) / calcDistance(b);
        return -f;
    }

    // Net force calculation methods
    public double calcNetForceExertedByX (Body[] b){
        double net = 0;
            for (Body body : b){
                if (this.equals(body)){
                    continue; 
                }
                net += this.calcForceExertedByX(body); 
            }
        return net;
    }

    public double calcNetForceExertedByY (Body[] b){
        double net = 0;
            for (Body body : b){
                if (this.equals(body)){
                    continue; 
                }
                net += this.calcForceExertedByY(body); 
            }
        return net;
    }

    // Updates body position and velocity
    public void update (double dt, double fx, double fy){
        double xAccel = fx / this.mass;
        double yAccel = fy / this.mass;
        this.xxVel += dt * xAccel;
        this.yyVel += dt * yAccel;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    // Draws bodies using StdDraw
    public void draw (){
        String fullFileName = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, fullFileName);
    }
}
public class NBody {

    // Reads radius from file using In
    public static double readRadius(String s){
        In in = new In(s);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    // Reads bodies from file using In
    public static Body[] readBodies(String s){
        In in = new In(s);
        int planets = in.readInt();
        Body[] b = new Body[planets];
        in.readDouble();
        for (int i = 0; i < planets; i++){
            double xpos = in.readDouble();
            double ypos = in.readDouble();
            double xvel = in.readDouble();
            double yvel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            b[i] = new Body(xpos, ypos, xvel, yvel, mass, img);
        }
        return b;
    }
    public static void main (String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0,0,"images/starfield.jpg");
        for (Body b: bodies){
            b.draw();
        }
        StdDraw.enableDoubleBuffering();
        double time = 0;

        // Time loop, assigns forces to force array and updates based on array values
        while (time <= T){
            double xForces[] = new double[bodies.length];
            double yForces[] = new double[bodies.length];
            for (int i = 0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0; i < bodies.length; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");
            for (Body b: bodies){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
        }   
    }
}
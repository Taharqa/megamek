/*
 * MegaMek - Copyright (C) 2000-2011 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.client.bot.princess;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import megamek.common.Coords;
import megamek.common.Entity;
import megamek.common.MovePath;
import megamek.common.logging.LogLevel;

/**
 * This contains useful classes and functions for geometric questions
 * the bot algorithm might have
 */
public class BotGeometry {

    /**
     * The combination of a coordinate and a facing
     */
    public static class CoordFacingCombo {

        private Coords coords;
        private int facing;

        private CoordFacingCombo(Coords c, int f) {
            setCoords(c);
            setFacing(f);
        }

        static CoordFacingCombo createCoordFacingCombo(Coords c, int f) {
            return new CoordFacingCombo(c, f);
        }

        static CoordFacingCombo createCoordFacingCombo(Entity e) {
            if (e == null) {
                return null;
            }
            return createCoordFacingCombo(e.getPosition(), e.getFacing());
        }

        static CoordFacingCombo createCoordFacingCombo(MovePath p) {
            if (p == null) {
                return null;
            }
            return createCoordFacingCombo(p.getFinalCoords(), p.getFinalFacing());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CoordFacingCombo)) {
                return false;
            }
            CoordFacingCombo c = (CoordFacingCombo) o;
            return !(getCoords() == null || !getCoords().equals(c.getCoords())) && getFacing() == c.getFacing();
        }

        @Override
        public int hashCode() {
            return (getCoords().hashCode() * 6) + getFacing();
        }

        Coords getCoords() {
            return coords;
        }

        void setCoords(Coords coords) {
            this.coords = coords;
        }

        int getFacing() {
            return facing;
        }

        void setFacing(int facing) {
            this.facing = facing;
        }

        @Override
        public String toString() {
            return "Facing " + getFacing() + "; " + (getCoords() == null ? "null" : getCoords().toString());
        }
    }

    /**
     * This describes a line in one of the 6 directions in board space
     * ---copied from Coords---
     * Coords stores x and y values. Since these are hexes, coordinates with odd x
     * values are a half-hex down. Directions work clockwise around the hex,
     * starting with zero at the top.
     * -y
     * 0
     * _____
     * 5 /     \ 1
     * -x /       \ +x
     * \       /
     * 4 \_____/ 2
     * 3
     * +y
     * ------------------------------
     * Direction is stored as above, but the meaning of 'intercept' depends
     * on the direction.  For directions 0,3, intercept means the y=0 intercept
     * for directions 1,2,4,5 intercept is the x=0 intercept
     */
    public static class HexLine {
        private final Princess owner;

        private int intercept;
        private int direction;

        /**
         * Create a hexline from a point and direction
         */
        public HexLine(Coords c, int dir, Princess owner) {
            @SuppressWarnings("unused")
            final String METHOD_NAME = "HexLine(Coords, int)";

            this.owner = owner;

            setDirection(dir);
            if ((getDirection() == 0) || (getDirection() == 3)) {
                setIntercept(c.x);
            } else if ((getDirection() == 1) || (getDirection() == 4)) {
                setIntercept(c.y + ((c.x + 1) / 2));
            } else {//direction==2||direction==5
                setIntercept(c.y - ((c.x) / 2));
            }
        }

        /**
         * returns -1 if the point is to the left of the line
         * +1 if the point is to the right of the line
         * and 0 if the point is on the line
         */
        public int judgePoint(Coords c) {
            final String METHOD_NAME = "judgePoint(Coords)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                HexLine comparor = new HexLine(c, getDirection(), owner);
                if (comparor.getIntercept() < getIntercept()) {
                    return (getDirection() < 3) ? -1 : 1;
                } else if (comparor.getIntercept() > getIntercept()) {
                    return (getDirection() < 3) ? 1 : -1;
                }
                return 0;
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * returns -1 if the area is entirely to the left of the line
         * returns +1 if the area is entirely to the right of the line
         * returns 0 if the area is divided by the line
         */
        public int judgeArea(ConvexBoardArea a) {
            final String METHOD_NAME = "judgeArea(ConvexBoardArea)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                boolean flip = getDirection() > 2;
                HexLine[] edges = a.getEdges();
                if ((edges[getDirection()] == null) || (edges[(getDirection() + 3) % 6] == null)) {
                    System.err.println(new IllegalStateException("Detection of NULL edges in ConvexBoardArea :: " +
                                                                 a.toString()));
                    return 0;
                }
                if (edges[getDirection()].getIntercept() == getIntercept()) {
                    return 0;
                }
                if (edges[(getDirection() + 3) % 6].getIntercept() == getIntercept()) {
                    return 0;
                }
                boolean edgeone = (edges[getDirection()].getIntercept() < getIntercept()) ^ flip;
                boolean edgetwo = (edges[(getDirection() + 3) % 6].getIntercept() < getIntercept()) ^ flip;
                if (edgeone && edgetwo) {
                    return 1;
                }
                if ((!edgeone) && (!edgetwo)) {
                    return -1;
                }
                return 0;
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * This function only makes sense for directions 1,2,4,5
         * Note that the function getXfromY would be multvalued
         */
        public int getYfromX(int x) {
            final String METHOD_NAME = "getYfromX(int)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                if ((getDirection() == 0) || (getDirection() == 3)) {
                    return 0;
                }
                if ((getDirection() == 1) || (getDirection() == 4)) {
                    return getIntercept() - ((x + 1) / 2); //halfs round down
                }
                // direction==5||direction==2
                return getIntercept() + ((x) / 2);     //halfs round down
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * Returns the intersection point with another line
         * if lines are parallel (even if they are coincident) returns null
         */
        public Coords getIntersection(HexLine h) {
            final String METHOD_NAME = "getIntersection(HexLine)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                if ((h.getDirection() % 3) == (getDirection() % 3)) {
                    return null;
                }
                if (h.getDirection() == 0) {
                    return h.getIntersection(this);
                }
                if (getDirection() == 2) {
                    return h.getIntersection(this);
                }
                if (getDirection() == 0) {
                    return new Coords(getIntercept(), h.getYfromX(getIntercept()));
                }
                //direction must be 1 here, and h.direction=2
                return new Coords(getIntercept() - h.getIntercept(), getYfromX(getIntercept() - h.getIntercept()));
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * Returns the (euclidian distance) closest point on this
         * line to another point
         */
        public Coords getClosestPoint(Coords c) {
            final String METHOD_NAME = "getClosestPoint(Coords)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                if ((getDirection() == 0) || (getDirection() == 3)) { //technically two points are equidistant,
                    // but who's counting
                    return new Coords(getIntercept(), c.y);
                } else if ((getDirection() == 1) || (getDirection() == 4)) {
                    double myx = (-2.0 / 3.0) * (getIntercept() - 0.5 - c.y - (2.0 * c.x));
                    return new Coords((int) myx, getYfromX((int) myx));
                }
                double myx = (-5.0 / 3.0) * (getIntercept() - (double) c.y - (2.0 * c.x));
                return new Coords((int) myx, getYfromX((int) myx));
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof HexLine)) {
                return false;
            }

            HexLine hexLine = (HexLine) o;

            if (getDirection() != hexLine.getDirection()) {
                return false;
            }
            //noinspection RedundantIfStatement
            if (getIntercept() != hexLine.getIntercept()) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = getIntercept();
            result = 31 * result + getDirection();
            return result;
        }

        int getIntercept() {
            return intercept;
        }

        void setIntercept(int intercept) {
            this.intercept = intercept;
        }

        int getDirection() {
            return direction;
        }

        void setDirection(int direction) {
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "Intercept " + getIntercept() + ", Direction " + getDirection();
        }
    }

    /**
     * This is a convex area on the board made up of 6 lines lying along one of the
     * 3 primary directions of a hex map
     */
    public static class ConvexBoardArea {

        private final Princess owner;

        //left/right indicates whether its the small x
        //or large x line
        //        HexLine[] left=new HexLine[3];
        //        HexLine[] right=new HexLine[3];
        //edge points to the previous lines in the right order
        private HexLine[] edges = new HexLine[6];
        private final ReentrantReadWriteLock EDGES_LOCK = new ReentrantReadWriteLock();

        ConvexBoardArea(Princess owner) {
            this.owner = owner;
            clearEdges();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ConvexBoardArea)) {
                return false;
            }

            ConvexBoardArea that = (ConvexBoardArea) o;

            //noinspection RedundantIfStatement
            if (!Arrays.equals(edges, that.edges)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(edges);
        }

        @Override
        public String toString() {
            StringBuilder msg = new StringBuilder("Edges:");
            HexLine[] edges = getEdges();
            for (int i = 0; i < edges.length; i++) {
                if (i != 0) {
                    msg.append("; ");
                }
                if (edges[i] == null) {
                    msg.append("null");
                } else {
                    msg.append(edges[i].toString());
                }
            }
            return msg.toString();
        }

        void addCoordFacingCombos(Iterator<CoordFacingCombo> cfit) {
            final String METHOD_NAME = "addCoordFacingCombos(Iterator<CoordFacingCombo>)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                while (cfit.hasNext()) {
                    CoordFacingCombo cf = cfit.next();
                    expandToInclude(cf.getCoords());
                }
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * returns true if a point is inside the area
         * false if it is not
         */
        boolean contains(Coords c) {
            final String METHOD_NAME = "contains(Coords)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                HexLine[] edges = getEdges();
                if (edges[0] == null) {
                    return false;
                }
                for (int i = 0; i < 6; i++) {
                    if (edges[i].judgePoint(c) > 0) {
                        return false;
                    }
                }
                return true;
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * expands the board area to include point onc
         */
        void expandToInclude(Coords onc) {
            final String METHOD_NAME = "expandToInclude(Coords)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                HexLine[] edges = getEdges();
                for (int i = 0; i < 6; i++) {
                    if ((edges[i] == null) || (edges[i].judgePoint(onc) > 0)) {
                        edges[i] = new HexLine(onc, i, owner);
                    }
                }
                setEdges(edges);
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * Returns a vertex, with zero starting at the upper left of the hex
         */
        Coords getVertexNum(int i) {
            final String METHOD_NAME = "getVertexNum(int)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                HexLine[] edges = getEdges();
                if (edges[i] == null || edges[(i + 1) % 6] == null) {
                    System.err.println(new IllegalStateException("Edge[" + i + "] is NULL."));
                    return null;
                }
                return edges[i].getIntersection(edges[(i + 1) % 6]);
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        /**
         * returns the closest coord in the area to the given coord
         */
        public Coords getClosestCoordsTo(Coords c) {
            final String METHOD_NAME = "getClosestCoordsTo(Coords)";
            owner.methodBegin(getClass(), METHOD_NAME);

            try {
                Coords closest = null;
                int closest_dist = 0;
                HexLine[] edges = getEdges();
                for (int i = 0; i < 6; i++) {
                    if (edges[i] == null) {
                        continue;
                    }
                    if (edges[i].judgePoint(c) > 0) {
                        Coords vert = getVertexNum(i);
                        int vdist = vert.distance(c);
                        if ((closest == null) || (vdist < closest_dist)) {
                            closest = vert;
                            closest_dist = vdist;
                        }
                        Coords online = edges[i].getClosestPoint(c);
                        if (contains(online)) {
                            int ldist = online.distance(c);
                            if (ldist < closest_dist) {
                                closest = online;
                                closest_dist = ldist;
                            }
                        }
                    }
                }
                if (closest == null) {
                    return new Coords(c.x, c.y);
                }
                return closest;
            } finally {
                owner.methodEnd(getClass(), METHOD_NAME);
            }
        }

        HexLine[] getEdges() {
            EDGES_LOCK.readLock().lock();
            try {
                return Arrays.copyOf(edges, edges.length);
            } finally {
                EDGES_LOCK.readLock().unlock();
            }
        }

        void setEdges(HexLine[] edges) {
            if (edges == null) {
                throw new IllegalArgumentException("Edges cannot be NULL, but it's members can.");
            }
            if (edges.length != 6) {
                throw new IllegalArgumentException("Edges must have exactly 6 members.");
            }

            EDGES_LOCK.writeLock().lock();
            try {
                this.edges = edges;
            } finally {
                EDGES_LOCK.writeLock().unlock();
            }
        }

        void clearEdges() {
            EDGES_LOCK.writeLock().lock();
            try {
                for (int i = 0; i < edges.length; i++) {
                    edges[i] = null;
                }
            } finally {
                EDGES_LOCK.writeLock().unlock();
            }
        }
    }

    /**
     * runs a series of self tests to make sure geometry is done correctly
     */
    static void debugSelfTest(Princess owner) {
        final String METHOD_NAME = "debugSelfTest()";
        final String PASSED = "passed";
        final String FAILED = "failed";

        StringBuilder msg = new StringBuilder("Performing self test of geometry");

        try {
            Coords center = new Coords(4, 6);
            HexLine[] lines = new HexLine[6];
            for (int i = 0; i < 6; i++) {
                lines[i] = new HexLine(center, i, owner);
            }

            msg.append("\n\tTesting that center lies in lines... ");
            boolean passed = true;
            for (int i = 0; i < 6; i++) {
                //System.err.println("direction="+i);
                //System.err.println("0="+lines[i].judgePoint(center));
                if (lines[i].judgePoint(center) != 0) {
                    passed = false;
                }
            }
            msg.append(passed ? PASSED : FAILED);

            msg.append("\n\tTesting more points that should lie on lines... ");
            passed = true;
            for (int i = 0; i < 6; i++) {
                if ((lines[i].judgePoint(center.translated(i)) != 0) || (lines[i].judgePoint(center.translated((i +
                                                                                                                3) %
                                                                                                               6)) !=
                                                                         0)) {
                    passed = false;
                    //System.err.println("direction="+i);
                    //System.err.println("0="+lines[i].judgePoint(center.translated(i)));
                    //System.err.println("0="+lines[i].judgePoint(center.translated((i+3)%6)));
                }
            }
            msg.append(passed ? PASSED : FAILED);

            passed = true;
            msg.append("\n\tTesting points to left and right of lines... ");
            for (int i = 0; i < 6; i++) {
                //            System.err.println("direction="+i);
                //          System.err.println("-1="+lines[i].judgePoint(center.translated((i+5)%6)));
                if (-1 != lines[i].judgePoint(center.translated((i + 5) % 6))) {
                    passed = false;
                }
                //        System.err.println("-1="+lines[i].judgePoint(center.translated((i+4)%6)));
                if (-1 != lines[i].judgePoint(center.translated((i + 4) % 6))) {
                    passed = false;
                }
                //      System.err.println("1="+lines[i].judgePoint(center.translated((i+1)%6)));
                if (1 != lines[i].judgePoint(center.translated((i + 1) % 6))) {
                    passed = false;
                }
                //    System.err.println("1="+lines[i].judgePoint(center.translated((i+2)%6)));
                if (1 != lines[i].judgePoint(center.translated((i + 2) % 6))) {
                    passed = false;
                }
            }
            msg.append(passed ? PASSED : FAILED);

            passed = true;
            Coords areapt1 = new Coords(1, 1);
            Coords areapt2 = new Coords(3, 1);
            Coords areapt3 = new Coords(2, 3);
            ConvexBoardArea area = new ConvexBoardArea(owner);
            area.expandToInclude(areapt1);
            area.expandToInclude(areapt2);
            area.expandToInclude(areapt3);
            owner.log(BotGeometry.class, METHOD_NAME, "Checking area contains proper points... ");
            msg.append("\n\tChecking area contains proper points... ");
            if (!area.contains(new Coords(1, 1))) {
                passed = false;
            }
            if (!area.contains(new Coords(2, 1))) {
                passed = false;
            }
            if (!area.contains(new Coords(3, 1))) {
                passed = false;
            }
            if (!area.contains(new Coords(1, 2))) {
                passed = false;
            }
            if (!area.contains(new Coords(2, 2))) {
                passed = false;
            }
            if (!area.contains(new Coords(3, 2))) {
                passed = false;
            }
            if (!area.contains(new Coords(2, 3))) {
                passed = false;
            }
            msg.append(passed ? PASSED : FAILED);

            passed = true;
            msg.append("\n\tChecking area doesn't contain extra points... ");
            if (area.contains(new Coords(0, 1))) {
                passed = false;
            }
            if (area.contains(new Coords(1, 0))) {
                passed = false;
            }
            if (area.contains(new Coords(2, 0))) {
                passed = false;
            }
            if (area.contains(new Coords(3, 0))) {
                passed = false;
            }
            if (area.contains(new Coords(4, 1))) {
                passed = false;
            }
            if (area.contains(new Coords(4, 2))) {
                passed = false;
            }
            if (area.contains(new Coords(4, 3))) {
                passed = false;
            }
            if (area.contains(new Coords(3, 3))) {
                passed = false;
            }
            if (area.contains(new Coords(2, 4))) {
                passed = false;
            }
            if (area.contains(new Coords(1, 3))) {
                passed = false;
            }
            if (area.contains(new Coords(0, 3))) {
                passed = false;
            }
            if (area.contains(new Coords(0, 2))) {
                passed = false;
            }
            msg.append(passed ? PASSED : FAILED);

        } finally {
            owner.log(BotGeometry.class, METHOD_NAME, LogLevel.DEBUG, msg);
        }
    }
}

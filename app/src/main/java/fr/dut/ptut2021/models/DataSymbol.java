package fr.dut.ptut2021.models;

import java.util.ArrayList;

import fr.dut.ptut2021.models.Point;

public final class DataSymbol {

    public static ArrayList<Point> pts = new ArrayList<>();
    public static int[] nbTrait = new int[10];

    public static ArrayList<Point> getPts(){
        return pts;
    }

    public static void initPts(int num, int width, int height) {

        nbTrait[0] = -1;
        nbTrait[1] = -1;
        nbTrait[2] = -1;
        nbTrait[3] = -1;
        nbTrait[4] = 10;
        nbTrait[5] = -1;
        nbTrait[6] = -1;
        nbTrait[7] = -1;
        nbTrait[8] = -1;
        nbTrait[9] = -1;

        switch (num){
            case 1:
                pts.add(new Point(0.33395004, 0.44072443));
                pts.add(new Point(0.40888068, 0.3959617));
                pts.add(new Point(0.5013876, 0.3423448));
                pts.add(new Point(0.506938, 0.41268623));
                pts.add(new Point(0.493062, 0.48991424));
                pts.add(new Point(0.4949121, 0.55484486));
                pts.add(new Point(0.48843667, 0.6276458));
                pts.add(new Point(0.50416285, 0.69552773));
                break;
            case 2:
                pts.add(new Point(0.35522664, 0.3693992));
                pts.add(new Point(0.45143387, 0.30004153));
                pts.add(new Point(0.59111935, 0.29807392));
                pts.add(new Point(0.6475486, 0.34726375));
                pts.add(new Point(0.6373728, 0.40481585));
                pts.add(new Point(0.5901942, 0.447611));
                pts.add(new Point(0.5134135, 0.50663877));
                pts.add(new Point(0.4014801, 0.5833749));
                pts.add(new Point(0.3052729, 0.65470016));
                pts.add(new Point(0.480111, 0.65224063));
                pts.add(new Point(0.65402406, 0.6438784));
                break;
            case 3:
                pts.add(new Point(0.2562442, 0.30299294));
                pts.add(new Point(0.40980574, 0.27593854));
                pts.add(new Point(0.57816833, 0.27052763));
                pts.add(new Point(0.7280296, 0.28823596));
                pts.add(new Point(0.64014804, 0.36005312));
                pts.add(new Point(0.53931546, 0.42744318));
                pts.add(new Point(0.67067534, 0.4692545));
                pts.add(new Point(0.70860314, 0.5563205));
                pts.add(new Point(0.6771508, 0.6276458));
                pts.add(new Point(0.60129505, 0.6665057));
                pts.add(new Point(0.44310823, 0.6773275));
                pts.add(new Point(0.30249768, 0.63895947));
                break;
            case 4:
                pts.add(new Point(0.453284, 0.25921395));
                pts.add(new Point(0.4144311, 0.3128309));
                pts.add(new Point(0.35245144, 0.37481007));
                pts.add(new Point(0.2987974, 0.43187025));
                pts.add(new Point(0.2414431, 0.51795244));
                pts.add(new Point(0.3959297, 0.53959596));
                pts.add(new Point(0.5374653, 0.5435312));
                pts.add(new Point(0.69010174, 0.53270936));
                pts.add(new Point(0.8223867, 0.53910404));
                pts.add(new Point(0.6762257, 0.40284824));
                pts.add(new Point(0.65402406, 0.4790925));
                pts.add(new Point(0.6419982, 0.5420554));
                pts.add(new Point(0.6475486, 0.6178078));
                pts.add(new Point(0.63552266, 0.679787));
                break;
            case 5:
                pts.add(new Point(0.65, 0.2938));
                pts.add(new Point(0.5610657, 0.2939));
                pts.add(new Point(0.41384888, 0.2997758));
                pts.add(new Point(0.27035522, 0.3071867));
                pts.add(new Point(0.26480103, 0.38064557));
                pts.add(new Point(0.27035522, 0.46151534));
                pts.add(new Point(0.38424683, 0.44721362));
                pts.add(new Point(0.5203247, 0.4486763));
                pts.add(new Point(0.625885, 0.46743107));
                pts.add(new Point(0.70736694, 0.5192098));
                pts.add(new Point(0.7314453, 0.578367));
                pts.add(new Point(0.69348145, 0.6291706));
                pts.add(new Point(0.6119995, 0.674026));
                pts.add(new Point(0.4981079, 0.6957061));
                pts.add(new Point(0.3703308, 0.6853699));
                pts.add(new Point(0.28329468, 0.6553038));
                break;
            case 6:
                pts.add(new Point(0.7169288, 0.33152303));
                pts.add(new Point(0.5855689, 0.28676027));
                pts.add(new Point(0.47363552, 0.2774142));
                pts.add(new Point(0.37372804, 0.31971747));
                pts.add(new Point(0.29417208, 0.35710174));
                pts.add(new Point(0.2682701, 0.42941076));
                pts.add(new Point(0.24421833, 0.471714));
                pts.add(new Point(0.253469, 0.5145092));
                pts.add(new Point(0.25161886, 0.563699));
                pts.add(new Point(0.29139686, 0.59173715));
                pts.add(new Point(0.36077705, 0.6487974));
                pts.add(new Point(0.46530992, 0.674868));
                pts.add(new Point(0.65217394, 0.6753599));
                pts.add(new Point(0.75115633, 0.6222349));
                pts.add(new Point(0.7419057, 0.5415636));
                pts.add(new Point(0.66882515, 0.4790925));
                pts.add(new Point(0.5319149, 0.471714));
                pts.add(new Point(0.4449584, 0.5145092));
                pts.add(new Point(0.38390377, 0.5582881));
                break;
            case 7:
                pts.add(new Point(0.88621646, 0.16526142));
                pts.add(new Point(0.2950971, 0.27593854));
                pts.add(new Point(0.40980574, 0.27101952));
                pts.add(new Point(0.5235893, 0.26708436));
                pts.add(new Point(0.69935244, 0.26856002));
                pts.add(new Point(0.75948197, 0.2808575));
                pts.add(new Point(0.7132285, 0.34283668));
                pts.add(new Point(0.68177617, 0.3925184));
                pts.add(new Point(0.6142461, 0.4549895));
                pts.add(new Point(0.5920444, 0.5031955));
                pts.add(new Point(0.5365402, 0.563699));
                pts.add(new Point(0.49028677, 0.6256782));
                pts.add(new Point(0.45235893, 0.6802789));
                break;
            case 8:
                pts.add(new Point(0.49213693, 0.26905194));
                pts.add(new Point(0.3894542, 0.29364687));
                pts.add(new Point(0.33117485, 0.35119897));
                pts.add(new Point(0.4172063, 0.4156376));
                pts.add(new Point(0.5356152, 0.43875682));
                pts.add(new Point(0.61979645, 0.49335757));
                pts.add(new Point(0.692877, 0.5646828));
                pts.add(new Point(0.6697502, 0.6512569));
                pts.add(new Point(0.52636445, 0.6832302));
                pts.add(new Point(0.34967622, 0.6733923));
                pts.add(new Point(0.253469, 0.59764));
                pts.add(new Point(0.32469934, 0.48794663));
                pts.add(new Point(0.45790935, 0.4490867));
                pts.add(new Point(0.61239594, 0.38612372));
                pts.add(new Point(0.6845513, 0.31627417));
                pts.add(new Point(0.53469014, 0.27544662));
                break;
            case 9:
                pts.add(new Point(0.68270123, 0.32020935));
                pts.add(new Point(0.56984276, 0.29807392));
                pts.add(new Point(0.4588344, 0.28971165));
                pts.add(new Point(0.35152635, 0.3374258));
                pts.add(new Point(0.3228492, 0.37923715));
                pts.add(new Point(0.3358002, 0.4461353));
                pts.add(new Point(0.37187788, 0.49778464));
                pts.add(new Point(0.48196116, 0.4992603));
                pts.add(new Point(0.6188714, 0.4736816));
                pts.add(new Point(0.7326549, 0.40579966));
                pts.add(new Point(0.7354302, 0.35415033));
                pts.add(new Point(0.7567068, 0.40924296));
                pts.add(new Point(0.74283075, 0.48056817));
                pts.add(new Point(0.7520814, 0.56074756));
                pts.add(new Point(0.7104533, 0.6374837));
                pts.add(new Point(0.60314524, 0.67880315));
                pts.add(new Point(0.46623495, 0.68618166));
                pts.add(new Point(0.35522664, 0.66011107));
                break;
        }


        for(Point p : pts){
            p.setX(p.getX()*width);
            p.setY(p.getY()*height);
        }

    }

}


/* // chiffre 3

*/


/* chiffre 6

*/

/* //chiffre 2

*/

/* // chiffre 8

*/

/* // chiffre 4   le 10eme point est les dubut du 2eme trait

 */

/* // chiifre 1

 */

/* // chiffre 7

 */

/* // chiffre 9

 */
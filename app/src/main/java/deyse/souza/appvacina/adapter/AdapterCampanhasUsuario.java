package deyse.souza.appvacina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.model.CampanhaUsuario;

public class AdapterCampanhasUsuario extends RecyclerView.Adapter <AdapterCampanhasUsuario.MyViewHolder>{

    private List<CampanhaUsuario> campanhasusuario;

    private Context context;

    public AdapterCampanhasUsuario(List<CampanhaUsuario>campanhasusuario, Context context){
        this.campanhasusuario = campanhasusuario;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeIst;
        TextView nomeCampanha;
        TextView dtinicio;
        TextView dtfim;
        TextView infoad;

        TextView endereco;

        TextView telefone;

        TextView horario;

        TextView municipio;

        TextView estado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIst = itemView.findViewById(R.id.textNomeusuarioC);
            nomeCampanha = itemView.findViewById(R.id.textNomeCampanha);
            dtinicio = itemView.findViewById(R.id.textDtinicio);
            dtfim = itemView.findViewById(R.id.textDtfim);
            infoad = itemView.findViewById(R.id.textInfoad);
            endereco = itemView.findViewById(R.id.textEnderecoIS);
            telefone = itemView.findViewById(R.id.textNtelefone);
            horario = itemView.findViewById(R.id.textHrAtend);
            municipio = itemView.findViewById(R.id.textMunicipio);
            estado = itemView.findViewById(R.id.textEstado);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campanhasusuario,parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCampanhasUsuario.MyViewHolder holder, int position) {

        CampanhaUsuario campanhaUsuario = campanhasusuario.get(position);
        holder.nomeIst.setText(campanhaUsuario.getNomeusuario());
        holder.nomeCampanha.setText(campanhaUsuario.getNome());
        holder.dtinicio.setText(campanhaUsuario.getDtinicio());
        holder.dtfim.setText(campanhaUsuario.getDtfim());
        holder.infoad.setText(campanhaUsuario.getDadosad());
        holder.endereco.setText(campanhaUsuario.getEndereco());
        holder.telefone.setText(campanhaUsuario.getTelefone());
        holder.horario.setText(campanhaUsuario.getHratend());
        holder.municipio.setText(campanhaUsuario.getMunicipio());
        holder.estado.setText(campanhaUsuario.getEstado());
    }

    @Override
    public int getItemCount() {
        return campanhasusuario.size();
    }


}
